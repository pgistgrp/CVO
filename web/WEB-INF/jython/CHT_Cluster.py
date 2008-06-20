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
# Output:
#     root : a root category reference

for i in range(len(userIdList)):
    print userIdList[i], ' : ', catList[i];
    for child in catList[i].children:
        print '    ', child;
        for tagRef in child.getTags():
            print '        ', tagRef;

# put your algorithm here
# end of your algorithm

categoryA = factory.createCategoryReference('A');
categoryA.tags.add(factory.createTagReference('tag 1.1'));
categoryA.tags.add(factory.createTagReference('tag 1.2'));
categoryA.tags.add(factory.createTagReference('tag 1.3'));
factory.addCategory(categoryA);

# A.B
categoryB = factory.createCategoryReference('B');
categoryB.tags.add(factory.createTagReference('tag 2.1'));
categoryB.tags.add(factory.createTagReference('tag 2.2'));
categoryB.tags.add(factory.createTagReference('tag 2.3'));
factory.addCategory(categoryB);
pathA_B = factory.createCategoryPath();
pathA_B.getCategories().add(categoryA);
pathA_B.getCategories().add(categoryB);
pathA_B.setFrequency(15);
pathA_B.setTitle('A.B');

# A.C
categoryC = factory.createCategoryReference('C');
categoryC.tags.add(factory.createTagReference('tag 2.1.1'));
categoryC.tags.add(factory.createTagReference('tag 2.1.2'));
categoryC.tags.add(factory.createTagReference('tag 2.1.3'));
categoryC.tags.add(factory.createTagReference('tag 2.1.4'));
categoryC.tags.add(factory.createTagReference('tag 2.1.5'));
factory.addCategory(categoryC);
pathA_C = factory.createCategoryPath();
pathA_C.getCategories().add(categoryA);
pathA_C.getCategories().add(categoryC);
pathA_C.setFrequency(13);
pathA_C.setTitle('A.C');

# D.A
categoryD = factory.createCategoryReference('D');
categoryD.tags.add(factory.createTagReference('tag 2.2.1'));
categoryD.tags.add(factory.createTagReference('tag 2.2.2'));
categoryD.tags.add(factory.createTagReference('tag 2.2.3'));
factory.addCategory(categoryD);
pathD_A = factory.createCategoryPath();
pathD_A.getCategories().add(categoryD);
pathD_A.getCategories().add(categoryA);
pathD_A.setFrequency(10);
pathD_A.setTitle('D.A');

# C.E.A
categoryE = factory.createCategoryReference('E');
categoryE.tags.add(factory.createTagReference('tag 3.1'));
categoryE.tags.add(factory.createTagReference('tag 3.2'));
categoryE.tags.add(factory.createTagReference('tag 3.3'));
factory.addCategory(categoryE);
pathC_E_A = factory.createCategoryPath();
pathC_E_A.getCategories().add(categoryC);
pathC_E_A.getCategories().add(categoryE);
pathC_E_A.getCategories().add(categoryA);
pathC_E_A.setFrequency(5);
pathC_E_A.setTitle('C.E.A');

# C.D.F
categoryF = factory.createCategoryReference('F');
categoryF.tags.add(factory.createTagReference('tag 4.1'));
categoryF.tags.add(factory.createTagReference('tag 4.2'));
categoryF.tags.add(factory.createTagReference('tag 4.3'));
categoryF.tags.add(factory.createTagReference('tag 4.4'));
factory.addCategory(categoryF);
pathC_D_F = factory.createCategoryPath();
pathC_D_F.getCategories().add(categoryC);
pathC_D_F.getCategories().add(categoryD);
pathC_D_F.getCategories().add(categoryF);
pathC_D_F.setFrequency(2);
pathC_D_F.setTitle('C.D.F');

# F.G
categoryG = factory.createCategoryReference('G');
categoryG.tags.add(factory.createTagReference('tag 5.1'));
categoryG.tags.add(factory.createTagReference('tag 5.2'));
categoryG.tags.add(factory.createTagReference('tag 5.3'));
factory.addCategory(categoryG);
pathF_G = factory.createCategoryPath();
pathF_G.getCategories().add(categoryF);
pathF_G.getCategories().add(categoryG);
pathF_G.setFrequency(1);
pathF_G.setTitle('F.G');

# A.H
categoryH = factory.createCategoryReference('H');
categoryH.tags.add(factory.createTagReference('tag 5.1.1'));
categoryH.tags.add(factory.createTagReference('tag 5.1.2'));
categoryH.tags.add(factory.createTagReference('tag 5.1.3'));
categoryH.tags.add(factory.createTagReference('tag 5.1.4'));
categoryH.tags.add(factory.createTagReference('tag 5.1.5'));
factory.addCategory(categoryH);
pathA_H = factory.createCategoryPath();
pathA_H.getCategories().add(categoryA);
pathA_H.getCategories().add(categoryH);
pathA_H.setFrequency(1);
pathA_H.setTitle('A.H');

# G.I
categoryI = factory.createCategoryReference('I');
categoryI.tags.add(factory.createTagReference('tag 5.2.1'));
categoryI.tags.add(factory.createTagReference('tag 5.2.2'));
categoryI.tags.add(factory.createTagReference('tag 5.2.3'));
factory.addCategory(categoryI);
pathG_I = factory.createCategoryPath();
pathG_I.getCategories().add(categoryG);
pathG_I.getCategories().add(categoryI);
pathG_I.setFrequency(1);
pathG_I.setTitle('G.I');

# G.J
categoryJ = factory.createCategoryReference('J');
categoryJ.tags.add(factory.createTagReference('tag 5.3.1'));
categoryJ.tags.add(factory.createTagReference('tag 5.3.2'));
categoryJ.tags.add(factory.createTagReference('tag 5.3.3'));
categoryJ.tags.add(factory.createTagReference('tag 5.3.4'));
factory.addCategory(categoryJ);
pathG_J = factory.createCategoryPath();
pathG_J.getCategories().add(categoryG);
pathG_J.getCategories().add(categoryJ);
pathG_J.setFrequency(1);
pathG_J.setTitle('G.J');

factory.addPath(pathA_B);
factory.addPath(pathA_C);
factory.addPath(pathD_A);
factory.addPath(pathC_E_A);
factory.addPath(pathC_D_F);
factory.addPath(pathF_G);
factory.addPath(pathA_H);
factory.addPath(pathG_I);
factory.addPath(pathG_J);
