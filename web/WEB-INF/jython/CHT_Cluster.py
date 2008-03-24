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
# Input:
#     userIdList
#     catList
#     factory : a factory to create new instances of category and tag.
#         createCategoryReference(string name)
#         createTagReference(string name)
# Output:
#     result : a root category reference

for i in range(len(userIdList)):
    print userIdList[i], ' : ', catList[i];
    for child in catList[i].children:
        print '    ', child;
        for tagRef in child.getTags():
            print '        ', tagRef;

# put your algorithm here
# end of your algorithm

result = factory.createCategoryReference('root'); #root

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
