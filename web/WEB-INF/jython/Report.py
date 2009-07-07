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
    pageSetting = PageSetting()
    # Loop through all the categories
    for entry in categories.entrySet():
        catId = entry.key
        catRef = entry.value
        #print catId
        #print cat
        # Append the user of each category reference built
        userList.append(catRef.getUser())
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


def getCHTInfo():
    # CHT INFORMATION
    chtInfo = dict()
    # Get the name of the Tool as defined
    chtInfo['name'] = report.getCht().getName()
    # Get the CST ID
    chtInfo['ID'] = chtID
    # Get the paths created by users
    paths = chtService.getPathsByChtId(chtID, None).toArray()
    # Each path can have multiple users so get them all
    usersList = []
    commentsList = []
    pageSetting = PageSetting()
    dupPaths = 0
    for path in paths:
        # If the path was contributed by multiple people it needs to count as multiple contributions
        if len(path.getUsers().toArray()) > 1:
            dupPaths = dupPaths + len(path.getUsers().toArray()) - 1
        # Add the users that have this path to the userlist
        usersList.extend(path.getUsers().toArray())
        # Grab the category references of this path
        catRefList = path.getCategories().toArray()
        for catRef in catRefList:
            # Use the chtService to grab comments by catRef
            commentsList.extend(chtService.getComments(catRef.getId(), pageSetting).toArray())
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
    # Get the number of votes
    chtInfo['numVotes'] = None
    # Get the number of votes that agree
    chtInfo['numVotesAgree'] = None
    # Get the number of moderator changes
    chtInfo['numModChg'] = None
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
    #
    ########################################################################
    #
    ########################################################################
    #
    ########################################################################
    #
    ########################################################################
    #
    """
    pathValues = []
    indics = []
    indicsNames = []
    for catPath in catPaths:
        # Each path has an ID to associate it with an indicator/unit pair
        # Use it to get a list of indicators
        pathValues.extend(VTTDAOImpl.getCategoryPathValuesByPathId(catPath.getId()).toArray()) 
        print pathValues
        #catInds = vttService.getMUnitSetsByPathId(catPath.getId()).toArray()
        #indics.extend(catInds) 
    vttInfo['numContributions'] = len(pathValues)
    """
    
    return vttInfo



bctInfo = getBCTInfo()
cstInfo = getCSTInfo()
chtInfo = getCHTInfo()
vttInfo = getVTTInfo()

def createHTMLTable(dataDict = None):
    # Create a table
    htmlTable = '<table align="center" border="1" cellspacing="1" cellpadding="3" width="80%">\n'
    # scroll through the keys and values
    for key, value in dataDict.iteritems():
        htmlTable += '\t<tr valing="top">\n'
        htmlTable += '\t\t<td align="right" style="border: 1px solid #000000">' + str(key) + '</td>\n'
        htmlTable += '\t\t<td align="left" style="border: 1px solid #000000">' + str(value) + '</td>\n'
        htmlTable += '\t</tr>\n'
    # End the table
    htmlTable += '</table>\n'
    return htmlTable

myfile = open(output,"w")
myfile.write('<h3> Brainstorm Concerns Information</h1>\n')
myfile.write(createHTMLTable(bctInfo))
myfile.write('<h3> Categories Creation Information</h1>\n')
myfile.write(createHTMLTable(cstInfo))
myfile.write('<h3> Create Hierarchies Information</h1>\n')
myfile.write(createHTMLTable(chtInfo))
myfile.write('<h3> VTT Information</h1>\n')
myfile.write(createHTMLTable(vttInfo))
myfile.close()
