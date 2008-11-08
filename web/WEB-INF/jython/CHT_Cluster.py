# Program to cluster CHT user categories.
# Objects:
#     CategoryReference
#         .category
#         .children
#         .frequency
#     Category
#         .name
#     TagReference
#         .tag
#     Tag
#         .name
#     CategoryPath
#         .frequency
# Input:
#     userIdList
#     catList
#     factory : a factory to create new instances of category and tag.
#         createCategoryReference(string name)
#         createTagReference(string name)
#         createCategoryPath()
#         addCategory(CategoryReference)
#         addPath(CategoryPath)

print
print "**************************************************************"
print
print "\t Jython CHT TOOL - Building Indicators"
print  

# Helping module for sorting list of lists
# Import string module
import string
import operator
# Helping module to identify type of data passed to us
from types import *
from org.pgist.sarp.cst import CategoryReference


# Import random module
#import random

# Program to cluster CHT user categories.
# Objects:
#     CategoryReference
#         .category   (name)
#         .children
#         .frequency
#     Category
#         .name
#     TagReference
#         .tag
#     Tag
#         .name
# Input:
#     userIdList
#     catList
#     factory : a factory to create new instances of category and tag.
#         createCategoryReference(string name)
#         createTagReference(string name)
# Output:
#     result : a root category reference


# constant
separator = '/'

# GENERAL CLASS DECLARATIONS
# Category object from Zhong's code		

class Category:
	def __init__(self, name=None):
		self.name = ''

# Tag object from Zhong's code
class Tag:
	def __init__(self, name=None):
		self.name = ''
	
# TagReference from Zhong's code
class TagReference:
	def __init__(self, tag=None):
		self.tag = Tag()

# categoryReference Class to be used for SAMPLE data
class CategoryReference:
	def __init__(self, category=None, children=None, tag=None):
		self.category = Category()
		self.children = []
		self.frequency = 0
		self.tag = TagReference()
	def __str__(self):
	      return self.category, len(self.children), self.frequency, self.tag.name
	def __len__(self):
		return len(self.children)




# Informational Class to be used for statistical analysis
class CategoryInformation:
	def __init__(self, label=None, catFreq=None, subFreq=None, subList=None):
		self.label = ''
		self.nodeFreq = 0
		self.leafFreq = 0
	def __str__(self):
	      return (self.label, self.nodeFreq, self.leafFreq)
	def __len__(self):
		return len(self.label)


# Indicator Class
class userIndicator:
	def __init___(self):
		self.userId = 0
		self.indList = []
	def __str__(self):
		return self.userId, self.indList
	def __len__(self):
		return len(self.indList)

# Indicator Statistics Class
# class PathStatistics:
class IndicatorStatistics:
	def __init__(self, name = None, freq = None, level = None, leaf = None):
		self.name = ""
		self.label = ""
		self.freq = 0
		self.level = 0
		self.leaf = ""
		self.initialNode = ""
		self.compositeRank = []
		self.rank = 0
		self.keep = 0
		self.userList = []
	def __str__(self):
		return self.name
	def __len__(self):
		return freq


# Helper class to remove unwanted characters
"""
class Translator:
    allchars = string.maketrans('','')
    def __init__(self, frm='', to='', delete='', keep=None):
        if len(to) == 1:
            to = to * len(frm)
        self.trans = string.maketrans(frm, to)
        if keep is None:
            self.delete = delete
        else:
            self.delete = self.allchars.translate(self.allchars, keep.translate(self.allchars, delete))
    def __call__(self, s):
        return s.translate(self.trans, self.delete)
"""        
"""

This class handles the three most common cases where I find myself having to stop and think about how to use translate:

1) Keeping only a given set of characters.

>>> trans = Translator(keep=string.digits)
>>> trans('Chris Perkins : 224-7992')
'2247992'

2) Deleting a given set of characters.

>>> trans = Translator(delete=string.digits)
>>> trans('Chris Perkins : 224-7992')
'Chris Perkins : -'

3) Replacing a set of characters with a single character.

>>> trans = Translator(string.digits, '#')
>>> trans('Chris Perkins : 224-7992')
'Chris Perkins : ###-####'
"""

# Helper class to remove unwanted characters

class Translator:
    allchars = string.maketrans('','')
    def __init__(self, frm='', to='', delete='', keep=None):
        if len(to) == 1:
            to = to * len(frm)
        self.trans = string.maketrans(frm, to)
        if keep is None:
            self.delete = delete
        else:
            self.delete = self.allchars.translate(self.allchars, keep.translate(self.allchars, delete))
    def __call__(self, s):
        return s.translate(self.trans, self.delete)
        
"""

This class handles the three most common cases where I find myself having to stop and think about how to use translate:

1) Keeping only a given set of characters.

>>> trans = Translator(keep=string.digits)
>>> trans('Chris Perkins : 224-7992')
'2247992'

2) Deleting a given set of characters.

>>> trans = Translator(delete=string.digits)
>>> trans('Chris Perkins : 224-7992')
'Chris Perkins : -'

3) Replacing a set of characters with a single character.

>>> trans = Translator(string.digits, '#')
>>> trans('Chris Perkins : 224-7992')
'Chris Perkins : ###-####'
"""

# END GENERAL CLASS DECLARATIONS




def buildSampleData(sampleCats = [], numUsers = None):
	# ********************************************************************************************
	# BUILDING SAMPLE DATA
	# Accepts a list of categories (optional) and a number of users (optional)
	# Returns a set, with a first element being a categories dictionary and the second being a list of user IDs

	# Check user-supplied input
	if len(sampleCats) == 0:
		#sampleCatsBuild = ['A','B','C','D','E']
		sampleCatsBuild = ['Water Runoff', 'Acid Rain', 'Air Pollution', 'Sewage', 'Boating Pollution', 'Agricultural Runoff', 'Invasive Species', 'Fishing', 'Non-Conventional Pollution', 'Oil Spills']
	else:
		sampleCatsBuild = sampleCats
		
	if numUsers is None:
		numUsers = 2

	# Make numUsers users - IDs start from 1000 and end at 1000 + numUsers
	endUserId = 1000 + numUsers
	userIdList = [x for x in range(1000, endUserId)]
	

	# Create the Category List for each user
	catList = []
	for pos, user in enumerate(userIdList):
		# DEBUG ******************************************************************************
		# print "User ", user
		# Start Bulding the catList dictionary

		# Find the current categories to use
		currentCats = []
		currentCats = sampleCatsBuild[:]

		# Decide randomly how many categories the user has, between 1 and length of currentCats
		numOfCat = random.randint(1, len(currentCats))
		# DEBUG ******************************************************************************
		# print "Number of Categories is", numOfCat

		# Randomize the categories to get different results each time
		random.shuffle(currentCats)

		# Get the categories we need - stored in randomCats[]
		randomCatNames = []
		for j in range (0, numOfCat):
			# Remove the first category from the sampleCats and put into the randomCats
			randomCatNames.append(currentCats.pop(0))
		# DEBUG ******************************************************************************
		# print "Length of Random Categories List is ", len(randomCats)

		# Create the first (empty/root) category reference
		rootCat = CategoryReference()
		rootCat.category.name = 'root'
		catList.insert(pos,rootCat)

		# Each randomCats Category needs children now. We find them randomly and put them in subCategs[]
		subCategs = []
		for k in range(0, numOfCat):
			# Find remaining Categories
			catsRemain = len(currentCats)
			# DEBUG ******************************************************************************
			# print "Iteration ", k, ". Remaining Categories to be sub-categorized ", catsRemain

			# If this is the last category, make sure it receives all remaining subcategories
			if k == (numOfCat - 1) and catsRemain > 0:
				# DEBUG ******************************************************************************
				# print "This is the last category to receive subcategories (Category", k, ", Remaining Cats", catsRemain, ")"
				subCatsForCat = catsRemain
			# If there are not subcategories remaining, then we don't need to make subcategories
			elif catsRemain == 0:
				subCatsForCat = 0
			else:
				# Else get random number of subcategories for the category
				subCatsForCat = random.randint(0,catsRemain)
				# DEBUG ******************************************************************************
				# print "Category ", k, " Subcategories for it ", subCatsForCat, " of ", catsRemain, " remaining"
			if subCatsForCat > 0:
				SubCatsForCatNames = []
				for l in range(0, subCatsForCat):
					SubCatsForCatNames.append(currentCats.pop(0))
				subCategs.append(SubCatsForCatNames)
			else:
				subCategs.append([])

		# Start populating the categories in the root	
		for m in range (0, numOfCat):
			catList[pos].children.append(CategoryReference())
			# Get the name of the category from randomCats[]
			catList[pos].children[m].category.name = randomCatNames[m]
			if len(subCategs[m]) > 0:
				for n in range (0, len(subCategs[m])):
					catList[pos].children[m].children.append((CategoryReference()))
					catList[pos].children[m].children[n].category.name = subCategs[m][n]

	return (catList, userIdList)
	# END BUILDING SAMPLE DATA
	# ********************************************************************************************




def getChildLeafList(category=CategoryReference(), childLeafList = []):
	# BEGIN HELPER FUNCTION FOR GETTING THE LEAF
	# Receives a CategoryReference() and returns a list of each leaf
	# ********************************************************************************************
	if category is None:
		return
	else:
		if len(category.children) > 0:
			for x in range(0, (len(category.children))):
				childLeaf = getChildLeafList(category.children[x], childLeafList)
				childLeafList.extend(childLeaf)
		else:
			childLeafList.append(category.category.name)
			#return (category.category.name)	
	return childLeafList
	# ********************************************************************************************
	# END HELPER FUNCTION



def getIndicators(catList = None, userIdList = None):
	# BEGIN GET INDICATORS FROM CATEGORY LIST
	# Indicators are categories with no children or the subcategories themselves
	# Requires a category list
	# Returns a list of list of each category's indicators
	# ********************************************************************************************
	
	# Check for input
	if catList is None:
		raise RuntimeError, "Failure to provide a categories list to generate indicators."
	if userIdList is None:
		raise RuntimeError, "Failure to provide a user list. Exiting..."
	
	
	# Create an empty Indicator List
	indicatorList = []

	# Loop through the categories list provided
	for x in range(len(userIdList)):
		userInd = userIndicator()
		userInd.userId = userIdList[x]
		categoryList = catList[x]
		userInd.indList = []
		# Incorporate the rank
		rank = 1
		# First category is always root, so move to children
		for child in categoryList.children:
			# Check if there are subcategories
			if ((len(child.children)) > 0):
				childLeafList = []
				parent = child.category.name
				for leaf in getChildLeafList(child, childLeafList):
					if isinstance(leaf, ListType):
						userIndList = [parent]
						for leafElement in leaf:
							userIndList.append(leafElement)
					else:
						userIndList = [parent]
						userIndList.append(leaf)
					rankInd = (rank, userIndList)
					userInd.indList.append(rankInd)
				rank += 1
			else:
				rankInd = (rank, child.category.name)
				userInd.indList.append(rankInd)
				rank += 1
			for childInd in userInd.indList:
				userIndParts = userIndicator()
				userIndParts.userId = user
				userIndParts.indList = childInd
				indicatorList.append(userIndParts)
	return indicatorList
	# END GET USER INDICATORS FROM CATEGORY LIST
	# ******************************************************************************************




def printInputData(catList = None):
	# ********************************************************************************************
	# FANCY PRINTOUT OF BALLOTS
	# Returns nothing
	
	# Check our input
	if catList is None:
		raise RuntimeError, "Failure to categories list to print"

	# Loop through the category list provided
	for pos, categoryList in enumerate(catList):
		print "USER No: ", pos
		# Loop through each Category List child
		catLoop = 0
		# First category is root, so move straight to children
		for child in categoryList.children:
			catLoop += 1
			print "   ", catLoop, ":", child.category.name
			if len(child.children) > 0:
				subCatLoop = 0
				for subChild in child.children:
					subCatLoop += 1
					print "      ", subCatLoop, ":", subChild.category.name
		# Use the child's getTags to get a TagReference
		#for tagRef in child.getTags():
		#    print '        ', tagRef.name;

		print
	
	return

	# END FANCY PRINTOUT
	# ********************************************************************************************



def getIndicatorFrequencies(indicatorList = None):
	# BEGIND GET INDICATOR FREQUENCIES
	# Accepts an indicators list
	# Returns a dictionary with key = indicator and value = IndicatorStatistics class instance
	# ********************************************************************************************

	# Check for input
	if indicatorList is None:
		raise RuntimeError, "Failure to provide indicator list for frequencies calculation"

	statisticsDict = dict()
	# Begin getting frequency calculations
	# each element of the indicator list is actually a user's indicators
	for userIndicators in indicatorList:
	# We have a unique user's indicators
		userId = userIndicators.userId
		indicatorRank = userIndicators.indList
		rank, indicator = indicatorRank
		if isinstance(indicator, ListType):
			indicatorName = ""
			indicatorName = "/".join(indicator)
		elif isinstance(indicator, StringType):
			indicatorName = indicator
		else:
			raise RuntimeError, "Failure to use given indicator list for statistical analysis. Expecting a list of lists" 
		# Check if the indicator is already there
		if statisticsDict.has_key(indicatorName):
			if statisticsDict[indicatorName].userList.count(userId):
				pass
			else:
				# It's there. Increase the frequency as the user didn't add it
				statisticsDict[indicatorName].freq += 1
				statisticsDict[indicatorName].compositeRank.append(rank)
				statisticsDict[indicatorName].userList.append(userId)
		else:
			# Not existing. Create and update fields
			statisticsDict[indicatorName] = IndicatorStatistics()
			# input .label
			statisticsDict[indicatorName].label = indicatorName
			# .freq
			statisticsDict[indicatorName].freq += 1
			# .level
			statisticsDict[indicatorName].level = indicatorName.count("/") + 1
			# .leaf
			statisticsDict[indicatorName].leaf = (indicatorName.split("/")).pop()
			# .initialNode
			statisticsDict[indicatorName].initialNode = (indicatorName.split("/")).pop(0)
			# .rank
			statisticsDict[indicatorName].compositeRank.append(rank)
			# .keep
			statisticsDict[indicatorName].keep = 0
			statisticsDict[indicatorName].userList.append(userId)
	return statisticsDict
	# END GET INDICATOR FREQUENCIES
	# ********************************************************************************************



def getCategoryFrequencies(indicatorList = None):
    # ********************************************************************************************
    # BEGIN CATEGORY FREQUENCIES METHOD
    # Requires an indicator list
    # Returns a dictionary of CategoryInformation class instances, with key the category name
    
    # Check for input
    if indicatorList is None:
        raise RuntimeError, "Failure to provide indicator list for frequencies calculation."
       
    # Create an empty dictionary we return
    categoryStatsDict = dict()
    
    # Begin getting frequency calculation
    for userIndicators in indicatorList:
        # This is a list of a user's indicators
		indicatorRank = userIndicators.indList
		rank, indicator = indicatorRank
		isCategory = 0
		# Check if this is a single-element indicator or a list
		if isinstance(indicator, ListType):
			# This is a category that appears as a leaf
			category = indicator[:]
			isCategory = 0
		elif isinstance(indicator, StringType):
			# This is a category that appears as a node with no leafs
			category = indicator
			isCategory = 1
		else:
			raise RuntimeError, "Failure to use given indicator list for statistical analysis"
		if isinstance(category, ListType):
			for i in (0, len(category) - 1):
				if category[i] not in categoryStatsDict:
					categoryStatsDict[category[i]] = CategoryInformation()
					categoryStatsDict[category[i]].label = category[i]
				if i == (len(category) - 1):
					categoryStatsDict[category[i]].leafFreq += 1
				else:
					categoryStatsDict[category[i]].nodeFreq += 1
		# Check if the category is already in our dictionary
		elif category not in categoryStatsDict:
			categoryStatsDict[category] = CategoryInformation()
			categoryStatsDict[category].label = category
			if isCategory:
				categoryStatsDict[category].leafFreq += 1
			else:
				categoryStatsDict[category].nodeFreq += 1   
    return categoryStatsDict
    # END CATEGORIES FREQUENCIES
    # ********************************************************************************************


def getRankedIndicators(catList = None, userIdList = None):
	# ********************************************************************************************
	# GET RANKING OF INDICATORS
	
	# Check input
	if catList is None:
		raise RuntimeError, "Failure to provide categories list for ranking calculation."
	if userIdList is None:
		raise RuntimeError, "Failure to provide categories list for ranking calculation."
		
	
def saveToDB(indicatorStats = None, categoryStats = None):
	# ********************************************************************************************
	# SAVE THE DATA TO THE DATABASE

	# Check for input
	if indicatorStats is None:
		raise RuntimeError, "Failure to provide indicator stats dict to save in DB"
	if categoryStats is None:
		raise RuntimeError, "Failure to provide category stats dict to save in DB"

	# Categories hash
	catHash = dict()
	# Buid all the categories with the factory
	for cat, catStat in categoryStats.iteritems():
		catHash[cat] = factory.createCategoryReference(catStat.label)
		factory.addCategory(catHash[cat])

	# Traverse the indicators and save them one by one
	for indic, indicStat in indicatorStats.iteritems():
		# indicator holds the name
		# stat holds: .freq .leaf .initialNode .level
		print "#### ", indic
		isPath = indic.count('/')
		if isPath > 0:
			indCats = indic.split('/')
		else:
			indCats = indic
		
		newPath = factory.createCategoryPath()
		if isPath > 0:
			print catHash
			for pathCat in indCats:
				if (catHash.has_key(pathCat)):
					newPath.getCategories().add(catHash[pathCat])
		else:
			newPath.getCategories().add(catHash[indCats])
		newPath.setFrequency(indicStat.freq)
		newPath.setTitle(indic)
		#newPath.setUsers(indicStat.users)
		factory.addPath(newPath)
        #print newPath.title
		
# end of your algorithm

#(catList, userIdList) = buildSampleData()

#printInputData(catList)

print "\t\t Getting indicators..."
print
indicators = getIndicators(catList, userIdList)

print "\t\t Calculating indicator statistics..."
print
indicatorStats = getIndicatorFrequencies(indicators)

print "\t\t Caclulating category statistics..."
print
categoryStats = getCategoryFrequencies(indicators)

#i = 0
#for indic, indicStat in indicatorStats.iteritems():
#	print i, indic
#	i += 1

print "\t\t Saving to database..."
print
saveToDB(indicatorStats, categoryStats)

print
print "\t Ending Jython CHT tool..."
print
print "**************************************************************"
print 