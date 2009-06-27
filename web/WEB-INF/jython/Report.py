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

from sets import Set as set

from types import *

#import org.python.util.*
#import org.python.core.*

# Helpinig structures
class toolReport:
  name = None
  ID = None
  created = None
  numContributions = None
  numContributors = None
  numComments = None
  numCommenters = None
  numVotes = None
  numVotesAgree = None
  numModChg = None


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
  concernVotes = []
  concernVotesAgree = []
  for concern in concerns:
    # Get the author of each concern and add him to the list of users
    authorsOfConcerns.append(concern.getAuthor())
    # Get the number of comments of each concern
    numOfComments.append(concern.getReplies())
    # Get the comment IDs for the concern
    pageSetting = PageSetting()
    
    commentIDs.extend(bctService.getConcernComments(concern.getId(), pageSetting).toArray())
    # Get the total number of voters for the concern
    concernVotes.append(concern.getNumVote())
    # Get the total number of positive votes for the concern
    concernVotesAgree.append(concern.getNumAgree())

  commentAuthors = []
  for comID in commentIDs:
    print "================"
    print comID
    commentAuthors.append(bctService.getConcernCommentById(comID).getAuthor())

  # Number of contributors is the length of the set of contribution authors
  bctInfo['numContributors'] = len(set(authorsOfConcerns))
  # Number of comments is the sum of numOfComments[] (comments of each concern)
  bctInfo['numComments'] = sum(numOfComments)
  # Number of commentators is the length of the set of comment authors 
  bctInfo['numCommenters'] = len(set(commentAuthors))
  # Number of votes is the sum of the concernVotes[] (votes by each concern)
  bctInfo['numVotes'] = sum(concernVotes)
  # Number of votes that agree is the sum of concernVotesAgree[] (agreement by concern)
  bctInfo['numVotesAgree'] = sum(concernVotesAgree)
  bctInfo['numModChg'] = None
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
  print type(categories)
  # Get the number of categories
  cstInfo['numContributions'] = len(categories)
  # Get the number of contributors
  userList = []
  commentsList = []
  # Loop through all the categories
  for catId, cat in categories:
    #print catId
    #print cat
    # Append the user of each category reference built
    userList.append(cat.getUser())
    # Get the comments for the category reference
    commentsList.extend(cstService.getComments(catId, pageSetting).toArray())
 
  # Get the authors of comments
  authorList = []
  for comment in commentsList:
    authorList.append(comment.getAuthor())
    
  cstInfo['numContributors'] = len(set(userList))
  # Get the number of comments
  cstInfo['numComments'] = len(commentsList)
  # Get the number of commentators
  cstInfo['numCommenters'] = None
  # Get the number of votes
  cstInfo['numVotes'] = None
  # Get the number of votes that agree
  cstInfo['numVotesAgree'] = None
  # Get the number of moderator changes
  cstInfo['numModChg'] = None
  
  # Return the CST Information dictionary
  return cstInfo

lineToWrite = ""

bctInfo = getBCTInfo()
cstInfo = getCSTInfo()

myfile = open(output,"w")
for key, value in bctInfo.iteritems():
  lineToWrite += str(key) + ": " + str(value) + "\n"
myfile.write(lineToWrite)
for key, value in cstInfo.iteritems():
  lineToWrite += str(key) + ": " + str(value) + "\n"
myfile.write(lineToWrite)
myfile.close()
