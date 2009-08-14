# -*- coding: utf-8 -*-
# Program to generate report.
# Input:
#     bctService: a database accessor for bct
#     cstService: a database accessor for cst
#     chtService: a database accessor for cht
#     vttService: a database accessor for vtt
#     drtService: a database accessor for drt
#     report : org.pgist.sarp.report.Report
#     output : a string, file path of the web pages for output

#print "================= report object", report.getId()
#print "================= output file", output


import java.util.Collections

from org.pgist.util import PageSetting

from org.pgist.sarp.vtt import VTTDAOImpl

from sets import Set as set

from types import *

#import org.python.util.*
#import org.python.core.*

#
# Begin getting accesss data
#

# Get our report ID
repID = report.getId()

# Get our workflow ID
wfID = report.getWorkflowId()

# Get the BCT ID (Brainstorm)
bctID = report.getBct().getId()

# Get the CST ID (Categories)
cstID = report.getCst().getId()

# Get the CHT ID (Hierarchies)
chtID = report.getCht().getId()

# Get the VTT ID (Indicators)
vttID = report.getVtt().getId()


#
# Begin collecting overall data for experiment
#

def getBCTInfo():
    # BCT INFORMATION
    bctInfo = dict()

    # Get the name of the Tool as defined
    bctInfo['name'] = report.getBct().getName()
    # Get the ID
    bctInfo['ID'] = bctID
    # Get the number of concerns
    bctInfo['numContributions'] = bctService.getConcernsTotal(report.getBct(), 0)
    # Get the concerns (ours plus other people)
    concerns = []
    concerns.extend(bctService.getMyConcerns(report.getBct()).toArray())
    concerns.extend(bctService.getOthersConcerns(report.getBct(), bctInfo['numContributions'] - len(concerns)).toArray())

    # Loop through all the concerns
    authorsOfConcerns = []
    numOfComments = []
    commentIDs = []
    concernComments = []
    concernVotes = []
    concernVotesAgree = []
    for concern in concerns:
        # Get the author of each concern and add him to the list of users
        authorsOfConcerns.append(concern.getAuthor())
        # Get the number of comments of each concern
        numOfComments.append(concern.getReplies())
        # Get the comment IDs for the concern
        pageSetting = PageSetting(-1)
        
        concernComments.extend(bctService.getConcernComments(concern.getId(), pageSetting).toArray())
        # Get the total number of voters for the concern
        concernVotes.append(concern.getNumVote())
        # Get the total number of positive votes for the concern
        concernVotesAgree.append(concern.getNumAgree())

    commentAuthors = []
    for com in concernComments:
        commentAuthors.append(com.getAuthor())

    # Number of contributors is the length of the set of contribution authors
    bctInfo['numContributors'] = len(set(authorsOfConcerns))
    # Number of comments is the sum of numOfComments[] (comments of each concern)
    bctInfo['numComments'] = sum(numOfComments)
    # Number of commentators is the length of the set of comment authors 
    bctInfo['numCommenters'] = len(set(commentAuthors))
    if bctInfo['numContributors'] == 0:
        bctInfo['avgContributions'] = 0
    else:
        bctInfo['avgContributions'] = bctInfo['numContributions'] / bctInfo['numContributors']
    if bctInfo['numCommenters'] == 0:
        bctInfo['avgComments'] = 0
    else:
        bctInfo['avgComments'] = bctInfo['numComments'] / bctInfo['numCommenters']
    # Get the info for the BCT DRT
    bctDrt = report.getBctDrt()
    drtBctInfo = getDRTInfo(bctDrt)
    # Number of votes is the sum of the concernVotes[] (votes by each concern)
    bctInfo['numVotes'] = drtBctInfo.get('numVotes')
    # Number of votes that agree is the sum of concernVotesAgree[] (agreement by concern)
    bctInfo['numVotesAgree'] = drtBctInfo.get('numVotesAgree')
    if bctInfo['numVotesAgree'] == 0:
        bctInfo['pctVotesAgree'] = 0.0
    else:
        bctInfo['pctVotesAgree'] = float(bctInfo['numVotes']) / bctInfo['numVotesAgree'] * 100
    bctInfo['numModChg'] = drtBctInfo.get('numModChg')
    
    # Return the bct information dictionary
    return bctInfo



def getCSTInfo():
    cstInfo = dict()
    # Get the name of the Tool as defined
    cstInfo['name'] = report.getCst().getName()
    # Get the CST ID
    cstInfo['ID'] = cstID
    # Get the categories
    categories = report.getCst().getCategories()
    # Get the number of categories
    cstInfo['numContributions'] = len(categories)
    # Get the number of contributors
    userList = []
    commentsList = []
    # Loop through all the categories
    for entry in categories.entrySet():
        pageSetting = PageSetting(-1)
        #print entry, type(entry)
        catId = entry.key
        catRef = entry.value
        #print 'catId:', catId
        #print 'catRef:', catRef
        # Append the user of each category reference built
        userList.append(catRef.getUser())
        # Get the comments for the category reference
        commentsList.extend(cstService.getComments(catRef.id, pageSetting).toArray())
    
    # Get the authors of comments
    authorList = []
    #print len(commentsList)
    for comment in commentsList:
        #print comment, type(comment)
        authorList.append(comment.getAuthor())
        
    cstInfo['numContributors'] = len(set(userList))
    # Get the number of comments
    cstInfo['numComments'] = len(commentsList)
    # Get the number of commentators
    cstInfo['numCommenters'] = len(set(authorList))
    
    if cstInfo['numContributors'] == 0:
        cstInfo['avgContributions'] = 0
    else:
        cstInfo['avgContributions'] = cstInfo['numContributions'] / cstInfo['numContributors']
    if cstInfo['numCommenters'] == 0:
        cstInfo['avgComments'] = 0
    else:
        cstInfo['avgComments'] = cstInfo['numComments'] / cstInfo['numCommenters']
    
    # Get the info for the CST DRT
    drtInfo = getDRTInfo(report.getCstDrt())
    # Number of votes is the sum of the concernVotes[] (votes by each concern)
    cstInfo['numVotes'] = drtInfo['numVotes']
    # Number of votes that agree is the sum of concernVotesAgree[] (agreement by concern)
    cstInfo['numVotesAgree'] = drtInfo['numVotesAgree']
    if cstInfo['numVotesAgree'] == 0:
        cstInfo['pctVotesAgree'] = 0.0
    else:
        cstInfo['pctVotesAgree'] = float(cstInfo['numVotes']) / cstInfo['numVotesAgree'] * 100
    cstInfo['numModChg'] = drtInfo['numModChg']
    
    # Return the CST Information dictionary
    return cstInfo


def getCHTInfo():
    # CHT INFORMATION
    chtInfo = dict()
    # Get the name of the Tool as defined
    chtInfo['name'] = report.getCht().getName()
    # Get the CST ID
    chtInfo['ID'] = chtID
    # Get the paths created by users
    paths = chtService.getPathsByChtId(chtID, None).toArray()
    #print "Number of paths:", len(paths)
    # Each path can have multiple users so get them all
    usersList = []
    commentsList = []
    dupPaths = 0
    for path in paths:
        #print path.getId(), path.getTitle()
        # If the path was contributed by multiple people it needs to count as multiple contributions
        if len(path.getUsers().toArray()) > 1:
            dupPaths = dupPaths + len(path.getUsers().toArray()) - 1
        # Add the users that have this path to the userlist
        usersList.extend(path.getUsers().toArray())        

    # Get the comments list, that is attached elsewhere apparently?
    for entry in report.getCht().getCategories().entrySet():
        catRefId = entry.value.id
        pageSetting = PageSetting(-1)
        comments = chtService.getComments(catRefId, pageSetting).toArray()
        commentsList.extend(comments)
        #commentsList.extend(chtService.getComments(catRefId, pageSetting))

    # Each comment has an author. Loop to get them
    authorsList = []
    for comment in commentsList:
        authorsList.append(comment.getAuthor())
    # Set the number of paths created 
    chtInfo['numContributions'] = len(paths) + dupPaths
    # Number of contributors is the length of the set of users making contributions
    chtInfo['numContributors'] = len(set(usersList))
    # Number of comments is the length of the list of comments
    chtInfo['numComments'] = len(commentsList)
    # Number of commenters is the length of the set of comment authors
    chtInfo['numCommenters'] = len(set(authorsList))
    
    if chtInfo['numContributors'] == 0:
        chtInfo['avgContributions'] = 0
    else:
        chtInfo['avgContributions'] = chtInfo['numContributions'] / chtInfo['numContributors']
    if chtInfo['numCommenters'] == 0:
        chtInfo['avgComments'] = 0
    else:
        chtInfo['avgComments'] = chtInfo['numComments'] / chtInfo['numCommenters']
    
    # Get the info for the CHT DRT
    drtInfo = getDRTInfo(report.getChtDrt())
    # Number of votes is the sum of the concernVotes[] (votes by each concern)
    chtInfo['numVotes'] = drtInfo['numVotes']
    # Number of votes that agree is the sum of concernVotesAgree[] (agreement by concern)
    chtInfo['numVotesAgree'] = drtInfo['numVotesAgree']
    
    if chtInfo['numVotesAgree'] == 0:
        chtInfo['pctVotesAgree'] = 0.0
    else:
        chtInfo['pctVotesAgree'] = float(chtInfo['numVotes']) / chtInfo['numVotesAgree'] * 100
    
    chtInfo['numModChg'] = drtInfo['numModChg']
    
    # Return
    return chtInfo

def getVTTInfo():
    # VTT INFORMATION
    vttInfo = dict()
    # Get the name of the tool
    vttInfo['name'] = report.getVtt().getName()
    # Get the ID of the tool
    vttInfo['ID'] = vttID
    # Get the paths that came to the step
    catPaths = report.getVtt().getPaths().toArray()
    # Get the number of users that contributed to this step
    users = report.getVtt().getUsers().toArray()
    vttInfo['numContributors'] = len(users)
    # TODO : Grab the individual contributions of each users
    # Retrieve comments each user had
    # Grab the number of experts participating in VTT
    vttInfo['numExperts'] = len(report.getVtt().getExperts().toArray())
    # We need to get the contributions - each path can have contributions    
    pathValues = []
    for catPath in catPaths:
        # Each path has an ID to associate it with an indicator/unit pair
        # Use it to get a list of indicators
        pathValues.extend(vttService.getCategoryPathValuesByPathId(catPath.getId()).toArray()) 
    
    # Find out the number of contributors
    contribs = []
    # Loop through each pathValue and find the user of it
    for pathValue in pathValues:
        contribs.append(pathValue.getUser().id)
    
    # Number of contributors is the length of the set of user IDs contributing
    vttInfo['numContributors'] = len(set(contribs))
    
    # Number of contributions is the length of the list of path values
    vttInfo['numContributions'] = len(pathValues)
    
    
    comments = []
    # Get the comments, one at a time
    for user in set(contribs):
        pageSetting = PageSetting(-1)
        comments.extend(vttService.getComments(user, vttID, pageSetting).toArray())
    
    vttInfo['numComments'] = len(comments)
    
    commAuthors = []
    # Find the people that commented
    for comm in comments:
        commAuthors.append(comm.getAuthor())
    
    vttInfo['numCommenters'] = len(set(commAuthors))
    
    if vttInfo['numContributors'] == 0:
        vttInfo['avgContributions'] = 0
    else:
        vttInfo['avgContributions'] = vttInfo['numContributions'] / vttInfo['numContributors']
    if vttInfo['numCommenters'] == 0:
        vttInfo['avgComments'] = 0
    else:
        vttInfo['avgComments'] = vttInfo['numComments'] / vttInfo['numCommenters']
    
    # Get the info for the CST DRT
    drtInfo = getDRTInfo(report.getVttDrt())
    # Number of votes is the sum of the concernVotes[] (votes by each concern)
    vttInfo['numVotes'] = drtInfo['numVotes']
    # Number of votes that agree is the sum of concernVotesAgree[] (agreement by concern)
    vttInfo['numVotesAgree'] = drtInfo['numVotesAgree']
    
    if vttInfo['numVotesAgree'] == 0:
        vttInfo['pctVotesAgree'] = 0.0
    else:
        vttInfo['pctVotesAgree'] = float(vttInfo['numVotes']) / vttInfo['numVotesAgree'] * 100
    
    vttInfo['numModChg'] = drtInfo['numModChg']
    
    return vttInfo

# Get the DRT Information - shared code
def getDRTInfo(drtGiven):
    drtInfo = dict()
    # Retrieve the number of votes
    drtInfo['numVotes'] = drtGiven.getNumVote()
    # Retrieve number of votes that agree
    drtInfo['numVotesAgree'] = drtGiven.getNumAgree()
    # Set the number of closed announcements
    drtInfo['numModChg'] = 0
    # Get all the announcements
    announcements = drtGiven.getAnnouncements().toArray()
    for announcement in announcements:
        # Check if the announcement is closed
        if announcement.isDone():
            drtInfo['numModChg'] += 1
    return drtInfo


bctInfo = getBCTInfo()
cstInfo = getCSTInfo()
chtInfo = getCHTInfo()
vttInfo = getVTTInfo()

def createHTMLTable(dataDict = None):
    # Create a table
    htmlTable = '<table align="center" border="0" cellspacing="1" cellpadding="3" width="100%">\n'
    # The elements we need
    neededList = ['numContributions', 'numContributors', 'avgContributions', 'numContributorsMax', 'numContributorsMin', 'numComments', 'numCommenters', 'avgComments', 'numCommentersMax', 'numCommentersMin', 'numModChg', 'numVotes', 'numVotesAgree', 'pctVotesAgree']
    needed = {'numContributions': 'Number of total contributions in this experiment', 
        'numContributors': 'Number of participants contributing',
        'avgContributions': 'Average number of contributions per participant contributing',
        'numComments': 'Number of total comments in this experiment',
        'numCommenters': 'Number of participants commenting',
        'avgComments': 'Average number of comments per participant commenting',
        'numVotes': 'Number of participants voting',
        'numVotesAgree': 'Number of votes agreeing with results of step',
        'pctVotesAgree': 'Percentage of voters agreeing with results of step',
        'numModChg': 'Number of modifications done by a moderator',
        'numContributorsMax': 'Maximum number of participants contributing in a step',
        'numContributorsMin': 'Minimum number of participants contributing in a step',
        'numCommentersMax': 'Maximum number of participants commenting in a step', 
        'numCommentersMin': 'Minimum number of participants commenting in a step'}
    # scroll through the keys and values
    for item in neededList:
        if item in dataDict:
            htmlTable += '\t<tr valing="top">\n'
            htmlTable += '\t\t<td align="right">' + str(needed[item]) + ':</td>\n'
            htmlTable += '\t\t<td align="left">' + str(dataDict[item]) + '</td>\n'
            htmlTable += '\t</tr>\n'
    # End the table
    htmlTable += '</table>\n<p>\n&nbsp;\n</p>\n'
    return htmlTable

def getVccStats(bctInfo, cstInfo, chtInfo, vttInfo):
    vccInfo = dict()
    # Number of total contributions
    vccInfo['numContributions'] = bctInfo['numContributions'] + cstInfo['numContributions'] + chtInfo['numContributions'] + vttInfo['numContributions']
    # Find the maximum and minimum contributions by tool
    contribs = [bctInfo['numContributors'], cstInfo['numContributors'], chtInfo['numContributors'], vttInfo['numContributors']]
    # Sort the contribs
    contribs.sort()
    vccInfo['numContributorsMax'] = contribs.pop()
    vccInfo['numContributorsMin'] = contribs.pop(0)
    # Total number of comments
    vccInfo['numComments'] = bctInfo['numComments'] + cstInfo['numComments'] + chtInfo['numComments'] + vttInfo['numComments']
    # Find the maximum and minimum commenters for tools
    comms = [bctInfo['numCommenters'], cstInfo['numCommenters'], chtInfo['numCommenters'], vttInfo['numCommenters']]
    # Sort the commenters
    comms.sort()
    vccInfo['numCommentersMax'] = comms.pop()
    vccInfo['numCommentersMin'] = comms.pop(0)
    return vccInfo

def getRankedIndicators():
    # Get the ranked indicators coming out of step 4
    catPaths = report.getVtt().getPaths().toArray()
    catPathValues = dict()
    mUnitSets = []
    for catPath in catPaths:
        pathValues = vttService.getCategoryPathValuesByPathId(catPath.getId()).toArray()
        for pathValue in pathValues:
            catPathIndic = pathValue.getName()
            path = pathValue.getPath()
            catPathName = path.getTitle()
            if catPathName not in catPathValues:
                catPathValues[catPathName] = dict()
            catPathValues[catPathName]["path"] = path
            if "indics" not in catPathValues[catPathName]:
                catPathValues[catPathName]["indics"] = dict()
            if catPathIndic not in catPathValues[catPathName]["indics"]:
                catPathValues[catPathName]["indics"][catPathIndic] = dict()
    for pathName, pathValues in catPathValues.iteritems():
        path = pathValues["path"]
        indics = pathValues["indics"]
        mUnSet = vttService.getMUnitSetsByPathId(path.getId()).toArray()
        for ind in mUnSet:
            indName = ind.getName()
            if len(indName) == 0:
                indName = "No Indicator provided"
            pathValues["indics"][indName] = dict()
            pathValues["indics"][indName]["unit"] = ""
            for unit in ind.getRecoFreqs().entrySet():
                unitName = unit.key
                if len(unitName) == 0:
                    unitName = "No unit name provided"
                pathValues["indics"][indName]["unit"] = unitName
            pathValues["indics"][indName]["freq"] = 0
            for userVote in ind.getUserSelections().entrySet():
                pathValues["indics"][indName]["freq"] += 1
        #for indic in indics.keys():
            #print "\tIndicator:", indic
            #mUnSet = vttService.getMUnitSetsByPathId(path.getId()).toArray()
            ## Read each unit individually and find out info
            #for unit in mUnSet:
                #for actualUnit in unit.getRecoFreqs().entrySet():
                    #if len(actualUnit.key) == 0:
                        #unitName = "No unit selected"
                    #unitName = actualUnit.key
                    #unitFreq = 0
                    #for userVote in unit.getUserSelections().entrySet():
                        #unitFreq += 1
                    #unitDict = dict()
                    #unitDict["unitName"] = unitName
                    #unitDict["unitFreq"] = unitFreq
                    #pathValue["units"].append(unitDict)
    return catPathValues
    

vccInfo = getVccStats(bctInfo, cstInfo, chtInfo, vttInfo)

pathIndicUnits = getRankedIndicators()

myfile = open(output,"w")
myfile.write('<h4> Voicing Climate Concerns Usage Overview</h4>\n')
myfile.write(createHTMLTable(vccInfo))
myfile.write('<p>')
# Graph of contibutors by step
grScale = 100.0 / vccInfo['numContributorsMax']
myfile.write('<img src="http://chart.apis.google.com/chart?chtt=Voicing+Climate+Concerns|Participants+Contributing+by+Step&amp;cht=bvs&amp;chd=t:' + str(bctInfo['numContributors'] * grScale) + ',' + str(cstInfo['numContributors'] * grScale) + ',' + str(chtInfo['numContributors'] * grScale) + ',' + str(vttInfo['numContributors'] * grScale) + '&amp;chdl=Step+1|Step+2|Step+3|Step+4&amp;chxt=x,y&amp;chxr=1,0,' + str(vccInfo['numContributorsMax']) + '&amp;chxl=0:|Step+1|Step+2|Step+3|Step+4|&amp;chxr=1,0,' + str(vccInfo['numContributorsMax']) + ',' + str(vccInfo['numContributorsMax'] / 4) + '&amp;chs=300x200&amp;chbh=a,20,10&amp;chf=bg,s,FFFFFF00">\n')
# Graph of contributions by step
grScale = 100.0 / vccInfo['numContributions']
myfile.write('<img src="http://chart.apis.google.com/chart?chtt=Voicing+Climate+Concerns|Total+Contributions+by+Step&amp;cht=p3&amp;chd=t:' + str(bctInfo['numContributions'] * grScale) + ',' + str(cstInfo['numContributions'] * grScale) + ',' + str(chtInfo['numContributions'] * grScale) + ',' + str(vttInfo['numContributions'] * grScale) + '&amp;chl=' + str(bctInfo['numContributions']) + '|' + str(cstInfo['numContributions']) + '|' + str(chtInfo['numContributions']) + '|' + str(vttInfo['numContributions']) + '&amp;chdl=Step+1|Step+2|Step+3|Step+4&amp;chs=300x150&amp;chf=bg,s,FFFFFF00">\n')
myfile.write('</p><p>')
# Graph of coommenters by step
if vccInfo['numCommentersMax'] == 0:
    grScale = 100.0
else:
    grScale = 100.0 / vccInfo['numCommentersMax']
myfile.write('<img src="http://chart.apis.google.com/chart?chtt=Voicing+Climate+Concerns|Participants+Commenting+by+Step&amp;cht=bvs&amp;chd=t:' + str(bctInfo['numCommenters'] * grScale) + ',' + str(cstInfo['numCommenters'] * grScale) + ',' + str(chtInfo['numCommenters'] * grScale) + ',' + str(vttInfo['numCommenters'] * grScale) + '&amp;chdl=Step+1|Step+2|Step+3|Step+4&amp;chxt=x,y&amp;chxr=1,0,' + str(vccInfo['numCommentersMax']) + '&amp;chxl=0:|Step+1|Step+2|Step+3|Step+4|&amp;chxr=1,0,' + str(vccInfo['numCommentersMax']) + ',' + str(vccInfo['numCommentersMax'] / 4) + '&amp;chs=300x200&amp;chbh=a,20,10&amp;chf=bg,s,FFFFFF00">\n')
# Graph of contributions by step
if vccInfo['numComments'] == 0:
    grScale = 100.0
else:
    grScale = 100.0 / vccInfo['numComments']
myfile.write('<img src="http://chart.apis.google.com/chart?chtt=Voicing+Climate+Concerns|Total+Comments+by+Step&amp;cht=p3&amp;chd=t:' + str(bctInfo['numComments'] * grScale) + ',' + str(cstInfo['numComments'] * grScale) + ',' + str(chtInfo['numComments'] * grScale) + ',' + str(vttInfo['numComments'] * grScale) + '&amp;chdl=' + str(bctInfo['numComments']) + '|' + str(cstInfo['numComments']) + '|' + str(chtInfo['numComments']) + '|' + str(vttInfo['numComments']) + '&amp;chdl=Step+1|Step+2|Step+3|Step+4&amp;chs=300x150&amp;chf=bg,s,FFFFFF00">\n')
myfile.write('</p>')


myfile.write('<p align="left">\n\t As can be seen from the graphs, participation in each step varied.\n</p>\n')
myfile.write('<p align="left">\n\t The moderator will elaborate on this further with your input.\n</p>\n')


myfile.write('<h4> Step 1 - Brainstorm Concern Keyphrases: Participation Information</h4>\n')
myfile.write('<p> This step allows users to specify concerns and associate them with meaningful keyphrases. The concerns and keyphrases users provide serve as the building blocks for the next step, categorizing keyphrases, as well as for the entire process.</p>\n') 
myfile.write(createHTMLTable(bctInfo))

myfile.write('<h4> Step 2 - Categorize Keyphrases: Participation Information</h4>\n')
myfile.write('<p>This sub-step allows users to organize keyphrases into labeled categories, generalizing specific contributions by theme. The categories users create are used in the next step as the building blocks for thematic relationships.</p>\n')
myfile.write(createHTMLTable(cstInfo))

myfile.write('<h4> Step 3 - Build Hierarchies: Participation Information</h4>\n')
myfile.write('<p> This step allows users to build hierarchies, further refining relationships between group categories, from general to specific levels. The hierarchies users create are separated into paths, contextualizing specific categories within general categories, to be used in the next step.</p>\n')
myfile.write(createHTMLTable(chtInfo))

myfile.write('<h4> Step 4 - Develop Indicators: Participation Information</h4>\n')
myfile.write('<p> This step allows users to develop indicators and units of measurement for each hierarchy path. The indicators users develop are combined with those of other users, voted on, and mapped in a subsequent workshop.</p>\n')
myfile.write(createHTMLTable(vttInfo))

myfile.write('<h4> Final Product: Ranked Indicators by theme</h4>\n')
myfile.write('<p> This text needs to be generated.</p>\n')
myfile.write('<table align="center" border="0" cellspacing="1" cellpadding="3" width="100%">\n')
def compare_by (fieldname):
    def compare_two_dicts (a, b):
        return cmp(a[fieldname], b[fieldname])
    return compare_two_dicts

for path, pathIndics in pathIndicUnits.iteritems():
    print "Path:", path
    myfile.write('<tr><td colspan="3"><br>Path: <strong>' + path + '</strong></td></tr>')
    indicNames = pathIndics["indics"].keys()
    for indName in indicNames:
        print "\t", indName
        indicVote = pathIndics["indics"][indName]["freq"]
        unit = pathIndics["indics"][indName]["unit"]
        myfile.write('<tr><td>Indicator: ' + str(indName) + '</td><td>Unit: ' + str(unit) + '</td><td>Voted:' + str(indicVote) + '</td></tr>')
        
        
        
    #for iUn in indUnit:
        #indicName = iUn["indic"]
        #myfile.write('<tr><td>&nbsp;</td><td colspan="3">Indicator: ' + indicName + '</td></tr>')
        #units = iUn["units"]
        #units.sort(compare_by('unitFreq'))
        #units.reverse()
        #for unit in units:
            #myfile.write('<tr><td>&nbsp;</td><td>&nbsp;</td><td>Unit: ' + unit["unitName"] + '</td><td>' + str(unit["unitFreq"]) + '</td></tr>')
    #print
    #print
    #inds.sort(compare_by('freq'))
    #inds.reverse()
    #for ind in inds:
        #myfile.write('<tr><td>' + ind["name"] + '</td><td>' + str(ind["freq"]) + '</td></tr>')
myfile.write('</table>')

myfile.close()

#exit()