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

category1 = factory.createCategoryReference('water');
result.children.add(category1);

category11 = factory.createCategoryReference('sea water');
category11.tags.add(factory.createTagReference('evaporation'));
category11.tags.add(factory.createTagReference('depth'));
category11.tags.add(factory.createTagReference('precipation'));
category1.children.add(category11);

category12 = factory.createCategoryReference('fresh water');
category12.tags.add(factory.createTagReference('evaporation'));
category12.tags.add(factory.createTagReference('depth'));
category12.tags.add(factory.createTagReference('precipation'));
category1.children.add(category12);

category = factory.createCategoryReference('pollution');
category.tags.add(factory.createTagReference('noise'));
category.tags.add(factory.createTagReference('waste'));
category.tags.add(factory.createTagReference('oil'));
result.children.add(category);

factory.setResult(result);

