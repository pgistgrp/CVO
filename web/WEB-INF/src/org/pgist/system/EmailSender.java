package org.pgist.system;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.beanutils.BeanUtils;
import org.pgist.users.User;


/**
 * 
 * @author kenny
 *
 */
public class EmailSender {
    
    
    private EmailDAO emailDAO;
    
    private Properties props = new Properties();
    
    private String contentType;
    
    private String from;
    
    private String fromThrough = "";
    
    private String username;
    
    private String password;
    
    private Map templates = new HashMap();
    
    private Pattern pattern = Pattern.compile("\\$\\{.*?\\}");
    
    
    public void setEmailDAO(EmailDAO emailDAO) {
        this.emailDAO = emailDAO;
    }


    public void setProps(Properties props) {
        this.props = props;
    }


    public void setContentType(String contentType) {
        this.contentType = contentType;
    }


    public void setFrom(String from) {
        this.from = from;
    }


    public void setFromThrough(String fromThrough) {
        this.fromThrough = fromThrough;
    }


    public void setUsername(String username) {
        this.username = username;
    }


    public void setPassword(String password) {
        this.password = password;
    }
    
    
    /*
     * ------------------------------------------------------------------------
     */
    
    
    private String merge(String text, Map values) throws Exception {
        StringBuffer buf = new StringBuffer();
        
        Matcher matcher = pattern.matcher(text);
        
        while(matcher.find()) {
            String matchStr = matcher.group();
            String bean = matchStr.substring(2, matchStr.length()-1);
            
            String s;
            int index = bean.indexOf(".");
            if (index<=0) {
                Object obj = values.get(bean);
                if (obj==null) {
                    s = matchStr;
                } else {
                    s = obj.toString();
                }
            } else {
                Object obj = values.get(bean.substring(0, index));
                if (obj==null) {
                    s = matchStr;
                } else {
                    s = BeanUtils.getNestedProperty(obj, bean.substring(index+1));
                }
            }
            
            matcher.appendReplacement(buf, s);
        }//while
        matcher.appendTail(buf);
        
        return buf.toString();
    }//merge()
    
    
    public void send(String to, String subject, String content) throws Exception {
        Session session = Session.getDefaultInstance(props, null);
        
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(from));
        if (fromThrough!=null && fromThrough.length()>3) {
            msg.addFrom(new InternetAddress[] { new InternetAddress(fromThrough) });
        }
        //msg.setReplyTo(new Address[] { new InternetAddress(from) });
        InternetAddress[] address = InternetAddress.parse(to);
        msg.setRecipients(Message.RecipientType.TO, address);
        msg.setSubject(subject);
        msg.setSentDate(new Date());
        msg.setContent(content, contentType);
        
        Transport transport = null;
        transport = session.getTransport();
        transport.connect(props.getProperty("mail.smtp.host"), username, password);
        
        transport.sendMessage(msg, address);
        transport.close();
    }//send()
    
    
    public void send(User user, String templateName, Map values) throws Exception {
        EmailTemplate template = (EmailTemplate) templates.get(templateName);
        
        if (template==null) {
            template = emailDAO.getTemplateByName(templateName);
            templates.put(templateName, template);
        }
        
        send(user.getEmail(), merge(template.getSubject(), values), merge(template.getContent(), values));
    }//send()


    public void send(Set<User> recipients, String templateName, Map<String, Object> vars, Long excludedUserId) {
        // turn off email sending temporarily
        return;
        
        /*
        EmailTemplate template = (EmailTemplate) templates.get(templateName);
        
        if (template==null) {
            try {
                template = emailDAO.getTemplateByName(templateName);
                templates.put(templateName, template);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        for (User one : recipients) {
            if (one.getId().equals(excludedUserId)) continue;
            if (!one.isEmailNotify()) continue;
            vars.put("recipient", one);
            
            try {
                send(one.getEmail(), merge(template.getSubject(), vars), merge(template.getContent(), vars));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        */
    }
    
    
    public void enqueue(Set<User> recipients, Long excludedUserId, String link) throws Exception {
        for (User user : recipients) {
            if (user.getId().equals(excludedUserId)) continue;
            if (!user.isEmailNotify()) continue;
            
            EmailNotification en = new EmailNotification();
            en.setFlag(false);
            en.setUser(user);
            en.setLink(link);
            
            emailDAO.save(en);
        }
    }
    
    
    public void checkAndSend() {
        try {
            // get all email notifications and set their flags to true
            // the emails are ordered by user id, so we can loop and group
            // the same recipient's emails in one email.
            List<EmailNotification> ens = emailDAO.getEmailNotifications();
            
            User user = null;
            Set<String> links = new HashSet<String>();
            Map<String, Object> vars = new HashMap<String, Object>();
            
            if (ens.size()==0) return;
            
            // merge and send email
            for (int i=0; i<ens.size(); i++) {
                try {
                    EmailNotification en = ens.get(i);
                    
                    // user doesn't want to receive email
                    if (!en.getUser().isEmailNotify()) continue;
                    
                    if (i==ens.size()-1) {
                        //last one
                        
                        if (user!=en.getUser()) {
                            // not in group, send the last one alone
                            vars.put("recipient", en.getUser());
                            String url = "http://www.climateconcerns.org/" + en.getLink();
                            StringBuilder sb = new StringBuilder();
                            sb.append("<li><a href=\"").append(url).append("\">").append(url).append("</a></li>");
                            vars.put("urls", sb.toString());
                            send(en.getUser(), "generic_comment", vars);
                        }
                        
                        if (links.size()>0) {
                            //send group
                            StringBuilder sb = new StringBuilder();
                            for (String link : links) {
                                String url = "http://www.climateconcerns.org/" + link;
                                sb.append("<li><a href=\"").append(url).append("\">").append(url).append("</a></li>");
                            }
                            vars.put("recipient", user);
                            vars.put("urls", sb.toString());
                            send(en.getUser(), "generic_comment", vars);
                        }
                    } else if (user!=en.getUser()) {
                        if (user==null) {
                            //first one, continue
                        } else {
                            // group boundary, send group
                            StringBuilder sb = new StringBuilder();
                            for (String link : links) {
                                String url = "http://www.climateconcerns.org/" + link;
                                sb.append("<li><a href=\"").append(url).append("\">").append(url).append("</a></li>");
                            }
                            vars.put("recipient", user);
                            vars.put("urls", sb.toString());
                            send(en.getUser(), "generic_comment", vars);
                            
                            // clear links to avoid sending duplicate emails.
                            links.clear();
                        }
                    } else {
                        // in group, continue
                    }
                    
                    links.add(en.getLink());
                    user = en.getUser();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            
            // delete all emails with flag true
            emailDAO.clearEmailNotifications();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
}//class EmailSender
