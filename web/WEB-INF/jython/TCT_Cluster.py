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

# Category Information class
class CategoryInformation:
	def __init__(self):
		self.name = ""
		self.tags = []
		self.users = []
		self.freqSet = 0
		self.alias = []
		self.freqName = 0
		self.jaccardScores = []
		self.tagsDiff = []
		self.freqNameAndSet = 0
	def __str__(self):
		return self.name
	def __len__(self):
		return len(self.tags)
	def __cmp__(self, other):
		if self.name == other:
			return 0
		elif self.name < other:
			return -1
		elif self.name > other:
			return 1



# END GENERAL CLASS DECLARATIONS

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
	
	uniqueCatsList = set(allCatsList)
	
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
	setA = set(listA)
	setB = set(listB)
	jaccard = float(len(setA.intersection(setB))) / float(len(setA.union(setB)))
	return jaccard

def difference(listA = [], listB = []):
	setA = set(listA)
	setB = set(listB)
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
	
	# Loop through the users
	print "111 ", len(userIdList)
	for iter in range(len(userIdList)):
		print iter
		user = userIdList[iter]
		# Get the root category for the user
		userRoot = catList[iter]
		# Iterate on all the user categories
		for userCats in catList[iter].children:
			# Check if the category exists
			if catInfoList.count(userCats.category.name) > 0:
				# The category already exists, so lets add to it (based on name)
				catInfoLoc = catInfoList.index(userCats.category.name)
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
					jScore = 0.0
				if jScore == 1.0:
					# The two sets are identical
					catInfoList[catInfoLoc].freqNameAndSet += 1
					catInfoList[catInfoLoc].users.append(user)
				else:
					# The two sets are not similar exactly, only by name
					catInfoList[catInfoLoc].freqName += 1
					catInfoList[catInfoLoc].jaccardScores.append(jScore)
					catInfoList[catInfoLoc].tagsDiff.append(difference(catInfoList[catInfoLoc].tags,inCatTags))
					catInfoList[catInfoLoc].users.append(user)
			elif catInfoList.count(userCats.category.name) == 0:
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
					if jScore == 1.0:
						# There is a match on the sets level
						catInfo.freqSet += 1
						catInfo.alias.append(userCats.category.name)
						catAdded = 1
						break
				# Check if we already added it
				if catAdded == 0:
					# Category was never added and no set similarity found. Add it as new
					catInfo = CategoryInformation()
					catInfo.name = userCats.category.name
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
	rootCat = factory.createCategoryReference('root')
	for cat in catInfoList:
		tempCat = factory.createCategoryReference(cat.name)
		for tag in cat.tags:
			tempCat.tags.add(factory.createTagReference(tag))
		for user in cat.users:
			# TODO: Add all users that had the category
			pass
		tempCat.frequency = cat.freqName
		#tempCat.freqSet = cat.freqSet
		#tempCat.freqName = cat.freqName
		#tempCat.freqNameAndSet = cat.freqNameAndSet
		for alias in cat.alias:
			# TODO: Add all aliases of the category
			pass
		for score in cat.jaccardScores:
			# TODO: Add the scores
			pass
		for tagsDiff in cat.tagsDiff:
			# TODO: Add the tag differences between the categories
			pass
		rootCat.getChildren().add(tempCat)
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


#if __name__ == "__main__":
catInfo = buildCategoriesInformation(catList, userIdList)
saveToDB(catInfo)
