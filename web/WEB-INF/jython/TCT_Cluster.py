# Program to cluster TCT user categories.
# Objects:
#     Category
# Input:
#     userIdList
#     catList
#     factory : a factory to create new instances of category and tag.
#         createCategoryReference(string name)
#         createTagReference(string name)
# Output:
#     result : a root category

for i in range(len(userIdList)):
    print userIdList[i], ' : ', catList[i];
    for child in catList[i].children:
        print '    ', child;
        for tag in child.getTags():
            print '        ', tag;

# put your algorithm here
# end of your algorithm

result = factory.createCategoryReference(''); #root

category = factory.createCategoryReference('water');
category.tags.add(factory.createTagReference('evaporation'));
category.tags.add(factory.createTagReference('depth'));
category.tags.add(factory.createTagReference('precipation'));
result.children.add(category);

category = factory.createCategoryReference('pollution');
category.tags.add(factory.createTagReference('noise'));
category.tags.add(factory.createTagReference('waste'));
category.tags.add(factory.createTagReference('oil'));
result.children.add(category);

factory.setResult(result);
