# Program to cluster TCT user categories.
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
# Input:
#     userIdList
#     catList
#     factory : a factory to create new instances of category and tag.
#         createCategoryReference(string name)
#         createTagReference(string name)
# Output:
#     result : a root category reference

print
print
print "**************************************************************"
print
print "\t Jython TCT Clustering Tool"
print

#import random
from sets import Set
import string

# GENERAL CLASS DECLARATIONS
# Category object from Zhong's code		
"""
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
		self.tags = []
	def __str__(self):
	      return self.category, len(self.children), self.frequency, self.tags.name
	def __len__(self):
		return len(self.children)
"""

# Category Information class
class CategoryInformation:
	def __init__(self):
		self.name = ""
		self.label = ""
		self.tags = []
		self.users = []
		self.freqSet = 0
		self.alias = []
		self.freqName = 0
		self.jaccardScores = []
		self.tagsDiff = []
		self.freqNameAndSet = 0
	def __str__(self):
		return self.label
	def __len__(self):
		return len(self.tags)
	def __cmp__(self, other):
		if self.name == other:
			return 0
		elif self.name < other:
			return -1
		elif self.name > other:
			return 1



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


def buildSampleData(catsToUse = [], tagsToUse = [], numUsers = None):
	# ********************************************************************************************
	# BUILDING SAMPLE DATA
	#
	
	# Number of users to generate
	numUsers = 10
	
	# Sample Word List to be used
	sampleWordList = []
	sampleWordList.append(["accountability", "apathetic", "assessment", "asset"])
	sampleWordList.append(["bio-diesel", "biodiversity", "bio-fuel", "biosphere"])
	sampleWordList.append(["capital", "carbon footprint", "catalyst", "clear cutting", "climate change"])
	sampleWordList.append(["dematirialization", "design", "differentiation", "diversity"])
	sampleWordList.append(["ecological footprint", "ecology", "economy", "ecosystem", "efficiency", "emmissions"])
	sampleWordList.append(["fair trade", "financial capital", "factor", "frame"])
	sampleWordList.append(["GDP", "GNP", "garbage", "global warming", "globalization", "governance", "grassroots"])
	sampleWordList.append(["human capital", "holistic management", "hypercar"])
	sampleWordList.append(["industrial ecology", "innovation", "intellectual capital"])
	sampleWordList.append(["justice", "juris-prudence"])
	sampleWordList.append(["knowledge economy", "kyoto-protocol"])
	sampleWordList.append(["leverage", "life cycle", "limits", "liquidity"])
	sampleWordList.append(["market segmentation", "marketing", "meeting", "methodology"])
	sampleWordList.append(["NGO", "natural capital", "niche"])
	sampleWordList.append(["opportunity", "organic", "overconsumption", "overharvest"])
	sampleWordList.append(["participatory", "partnership", "payback period", "personalization", "pollution", "productivity"])
	sampleWordList.append(["quality of life"])
	sampleWordList.append(["recycle", "reduce", "renewable", "reporting"])
	sampleWordList.append(["scenario", "sensitivity", "service", "smart groth", "stakeholders"])
	sampleWordList.append(["tactic", "tax", "technology", "tipping point", "transparency", "trust"])
	sampleWordList.append(["unintended consequences", "upcycle"])
	sampleWordList.append(["value", "vision", "version"])
	sampleWordList.append(["waste reduction", "working capital"])
	sampleWordList.append(["xenophobia"])
	sampleWordList.append(["youth", "yield"])
	sampleWordList.append(["zero waste", "zero emmissions"])
	
	catList = []
	userIdList = []
	
	# Define the sample categories and tags
	sampleCats = catsToUse[:]
	sampleTags = tagsToUse[:]
	
	for user in range(0, numUsers):
		if len(sampleCats) == 0:
			# Build sample category names
			sampleCats = []
			for catNum in range(0, len(sampleWordList) -1):
				# Randomly decide whether to user this letter
				if (random.randint(0,1)):
					# Choose a random element to use
					randElementNum = random.randint(0, (len(sampleWordList[catNum]) - 1))
					randElement = sampleWordList[catNum][randElementNum]
					if (randElementNum > (len(sampleWordList[catNum]) / 3)):
						randElement = randElement.capitalize()
					sampleCats.append(randElement)
		if len(sampleTags) == 0:
			# Build sample tags
			sampleTags = []
			for tagNum in range(0, len(sampleWordList) - 1):
				# As tags need to be more than categories, we grab a tag every time
				# Find how many tags to read from this letter
				tagsToRead = random.randint(0, (len(sampleWordList[tagNum]) -1))
				# If it wasn't 0 then start reading them
				if tagsToRead > 0:
					# Return a random sample
					readTags = random.sample(sampleWordList[tagNum], tagsToRead)
					sampleTags.extend(readTags)
		#print sampleTags
		# Build the user's root category
		rootCat = CategoryReference()
		rootCat.category.name = 'root'
		
		# Now that we have categories and tags for the user, assign them
		for category in sampleCats:
			# Create a category and give it a name
			newCat = Category()
			newCat.name = category
			# Create a category reference and assign a category to it
			newCatRef = CategoryReference()
			newCatRef.category = newCat
			# Create the tags we need, assign them to tag references and add them to the category
			numOfTags = random.randint(0, (len(sampleTags) / 2))
			for tagNum in range (0, numOfTags):
				# Shuffle the tags so we get a random selection of them
				random.shuffle(sampleTags)
				# Create a new Tag and assign it a name
				newTag = Tag()
				newTag.name = sampleTags[tagNum]
				# Create a new tag reference and assign it a tag
				newTagRef = TagReference()
				newTagRef.tag = newTag
				# Check if the tag reference already exist
				exists = 0
				for existingTag in newCatRef.tags:
					if existingTag.tag.name == newTagRef.tag.name:
						exists = 1
				if exists == 0:
					# Assign this tag reference to the category
					newCatRef.tags.append(newTagRef)
			# Add the category to the user's categories if it has tags
			if len(newCatRef.tags) > 0:
				rootCat.children.append(newCatRef)
		
		# Add the root category to the catList
		catList.append(rootCat)
		userIdList.append(user)
		
		# Reset the categories and tags to the original form
		sampleCats = catsToUse[:]
		sampleTags = tagsToUse[:]

	# Return all the values	
	return (catList, userIdList)

	# END BUILDING SAMPLE DATA
	# ********************************************************************************************




def findUniqueCategories(catList = []):
	# ********************************************************************************************
	# FIND UNIQUE CATEGORIES IN CATLIST
	# Returns a dictionary with key the category and value number of times it appears
	
	# Check input
	if len(catList) <= 0:
		raise RuntimeError, "Category List not provided. Exiting..."
	
	allCatsList = []
	
	# Create a list of all categories
	for iter in range(0, len(catList) - 1):
		# Loop through all children
		for child in catList[iter].children:
			allCatsList.append(child.category.name)
	
	uniqueCatsList = Set(allCatsList)
	
	# Build a dictionary of the unique ones
	uniqueCatsDict = dict([(x, 0) for x in uniqueCatsList])
	
	for cat in allCatsList:
		uniqueCatsDict[cat] += 1
	
	return uniqueCatsDict
	
	# END FIND UNIQUE CATEGORIES
	# ********************************************************************************************

def isSimilar(listA = [], listB = []):
	if len(listA) == 0 or len(listB) == 0:
		return 1
	setA = Set(listA)
	setB = Set(listB)
	jaccard = float(len(setA.intersection(setB))) / float(len(setA.union(setB)))
	
	return int(jaccard*1000)

def difference(listA = [], listB = []):
	setA = Set(listA)
	setB = Set(listB)
	return list(setB.symmetric_difference(setA))


def buildCategoriesInformation(catList = [], userIdList = []):
	# ********************************************************************************************
	# BUILD A LIST OF LISTS OF SETS BY USER ID, THEN CATEGORY, THEN TAGS
	
	# Check input
	if len(catList) <= 0:
		raise RuntimeError, "Category List not provided. Exiting..."
	if len(userIdList) <= 0:
		raise RuntimeError, "User ID List not provided. Exiting..."
	if len(catList) != len(userIdList):
		raise RuntimeError, "Category List and User ID List of different lengths. Exiting..."
	
	# Create the list to hold information. List matches user ID
	catInfoList = []
	
	remNums = Translator(delete=string.digits)
	remPunc = Translator(delete=string.punctuation)
	
	# Loop through the users
	for iter in range(len(userIdList)):
		#print iter
		user = userIdList[iter]
		# Get the root category for the user
		userRoot = catList[iter]
		# Iterate on all the user categories
		for userCats in catList[iter].children:
			# Clean up the userCats category name
			cleanName = string.upper(remPunc(remNums(userCats.category.name)))
			# Check if the category exists
			if catInfoList.count(cleanName) > 0:
				# The category already exists, so lets add to it (based on name)
				catInfoLoc = catInfoList.index(cleanName)
				# Check if the tag set is also the same
				inCatTags = []
				# Add the tags in a set
				for tagRef in userCats.tags:
					inCatTags.append(tagRef.tag.name)
				# Calculate similarity
				if ((len(inCatTags)) > 0) and (len(catInfoList[catInfoLoc].tags) > 0):
					jScore = isSimilar(inCatTags, catInfoList[catInfoLoc].tags)
				else:
					# One of the sets is empty, therefore similarity is 0
					jScore = 0
				if jScore >= 800:
					# The two sets are identical
					catInfoList[catInfoLoc].freqNameAndSet += 1
					catInfoList[catInfoLoc].users.append(user)
				else:
					# The two sets are not similar exactly, only by name
					catInfoList[catInfoLoc].freqName += 1
					catInfoList[catInfoLoc].jaccardScores.append(jScore)
					catInfoList[catInfoLoc].tagsDiff.extend(difference(catInfoList[catInfoLoc].tags,inCatTags))
					catInfoList[catInfoLoc].users.append(user)
			elif catInfoList.count(cleanName) == 0:
				catAdded = 0
				# Category doesn't exist by name. Check if it exists by tag set
				inCatTags = []
				for tagRef in userCats.tags:
					inCatTags.append(tagRef.tag.name)
				# Check all known categories' tag set for similarity
				for catInfo in catInfoList:
					catTags = catInfo.tags
					# Calculate similarity
					if ((len(inCatTags)) > 0) and (len(catTags) > 0):
						jScore = isSimilar(inCatTags, catTags)
					else:
						# One of the sets is empty, therefore similarity is 0
						jScore = 0
					if jScore >= 800:
						# There is a match on the sets level
						catInfo.freqSet += 1
						catInfo.alias.append(userCats.category.name)
						catAdded = 1
						break
				# Check if we already added it
				if catAdded == 0:
					# Category was never added and no set similarity found. Add it as new
					catInfo = CategoryInformation()
					catInfo.label = userCats.category.name
					catInfo.name = cleanName
					catInfo.freqName += 1
					catInfo.users.append(user)
					for tagRef in userCats.tags:
						catInfo.tags.append(tagRef.tag.name)
					# Add it to the catInfoList
					catInfoList.append(catInfo)
	return catInfoList
	
def saveToDB(catInfoList = None):
# ********************************************************************************************
	# SAVE CATEGORY INFORMATION TO THE DB THROUGH THE FACTORY
	
	# Check for input
	if len(catInfoList) == 0:
		raise RuntimeError, "Nothing provided to be saved in the DB. Exiting..."
	
	# We need to loop through the catInfoList and create caegoryReferences
	rootCat = factory.createCategoryInfo('root')
	for cat in catInfoList:
		print "------------ >", cat.name
		info = factory.createCategoryInfo(cat.label)
		for tag in cat.tags:
			info.catRef.tags.add(factory.createTagReference(tag))
		# Add the different tags used too
		for diffTag in cat.tagsDiff:
			print "====> ", diffTag
			info.catRef.tags.add(factory.createTagReference(diffTag))			
		for user in cat.users:
			info.addUser(user)
		info.freqSet = cat.freqSet
		info.freqName = cat.freqName
		info.freqNameAndSet = cat.freqNameAndSet
		for alias in cat.alias:
			info.addAlias(alias)
		for score in cat.jaccardScores:
			info.addScore(score)
		rootCat.addChild(info)
	print rootCat
	factory.setResult(rootCat)
	return
	#
	# ********************************************************************************************
	
	
"""	
	self.name = ""
	self.tags = []
	self.users = []
	self.freqSet = 0
	self.alias = []
	self.freqName = 0
	self.jaccardScores = []
	self.tagsDiff = []
	self.freqNameAndSet = 0
"""	

if __name__ == "__main__":
	#(catList, userIdList) = buildSampleData()
	print "\t\t Building Category Information..."
	print
	catInfo = buildCategoriesInformation(catList, userIdList)
	print "\t\t Saving to database..."
	print
	saveToDB(catInfo)
else:
	catInfo = buildCategoriesInformation(catList, userIdList)
	saveToDB(catInfo)

# """
# for cat in catInfo:
# 	print cat.name, cat.label
# 	print "\t", cat.users
# 	print "\t", cat.alias
# 	print "\t", cat.tags
# 	print
# """

print
print "\t Ending Jython TCT Cluster Tool"
print
print "**************************************************************"
print
