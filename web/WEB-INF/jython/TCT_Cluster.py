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

for i in range(len(userIdList)):
    print userIdList[i], ' : ', catList[i];
    for child in catList[i].children:
        print '    ', child;
        for tagRef in child.getTags():
            print '        ', tagRef;

# put your algorithm here
# end of your algorithm

result = factory.createCategoryReference('root'); #root

category = factory.createCategoryReference('Category 1');
category.tags.add(factory.createTagReference('tag 1.1'));
category.tags.add(factory.createTagReference('tag 1.2'));
category.tags.add(factory.createTagReference('tag 1.3'));
result.children.add(category);

category = factory.createCategoryReference('Category 2');
category.tags.add(factory.createTagReference('tag 2.1'));
category.tags.add(factory.createTagReference('tag 2.2'));
category.tags.add(factory.createTagReference('tag 2.3'));
result.children.add(category);

category1 = factory.createCategoryReference('Category 2-1');
category1.tags.add(factory.createTagReference('tag 2.1.1'));
category1.tags.add(factory.createTagReference('tag 2.1.2'));
category1.tags.add(factory.createTagReference('tag 2.1.3'));
category1.tags.add(factory.createTagReference('tag 2.1.4'));
category1.tags.add(factory.createTagReference('tag 2.1.5'));
category.children.add(category1);

category2 = factory.createCategoryReference('Category 2-2');
category2.tags.add(factory.createTagReference('tag 2.2.1'));
category2.tags.add(factory.createTagReference('tag 2.2.2'));
category2.tags.add(factory.createTagReference('tag 2.2.3'));
category.children.add(category2);

category = factory.createCategoryReference('Category 3');
category.tags.add(factory.createTagReference('tag 3.1'));
category.tags.add(factory.createTagReference('tag 3.2'));
category.tags.add(factory.createTagReference('tag 3.3'));
result.children.add(category);

category = factory.createCategoryReference('Category 4');
category.tags.add(factory.createTagReference('tag 4.1'));
category.tags.add(factory.createTagReference('tag 4.2'));
category.tags.add(factory.createTagReference('tag 4.3'));
category.tags.add(factory.createTagReference('tag 4.4'));
result.children.add(category);

category = factory.createCategoryReference('Category 5');
category.tags.add(factory.createTagReference('tag 5.1'));
category.tags.add(factory.createTagReference('tag 5.2'));
category.tags.add(factory.createTagReference('tag 5.3'));
result.children.add(category);

category1 = factory.createCategoryReference('Category 5-1');
category1.tags.add(factory.createTagReference('tag 5.1.1'));
category1.tags.add(factory.createTagReference('tag 5.1.2'));
category1.tags.add(factory.createTagReference('tag 5.1.3'));
category1.tags.add(factory.createTagReference('tag 5.1.4'));
category1.tags.add(factory.createTagReference('tag 5.1.5'));
category.children.add(category1);

category2 = factory.createCategoryReference('Category 5-2');
category2.tags.add(factory.createTagReference('tag 5.2.1'));
category2.tags.add(factory.createTagReference('tag 5.2.2'));
category2.tags.add(factory.createTagReference('tag 5.2.3'));
category.children.add(category2);

category3 = factory.createCategoryReference('Category 5-3');
category3.tags.add(factory.createTagReference('tag 5.3.1'));
category3.tags.add(factory.createTagReference('tag 5.3.2'));
category3.tags.add(factory.createTagReference('tag 5.3.3'));
category3.tags.add(factory.createTagReference('tag 5.3.4'));
category.children.add(category3);

factory.setResult(result);
