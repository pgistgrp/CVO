# Program to generate report.
# Input:
#     connection : a JDBC connection
#     webpath : a string, directory path of the web pages

"""
from java.lang import *
from java.sql import *

Class.forName("org.postgresql.Driver")

connection = DriverManager.getConnection('jdbc:postgresql://localhost:5431/sarp', 'michalis', 's3pt3m8er')
"""

stmt = connection.createStatement()

sql = 'SELECT pgist_user_base.id, pgist_user_base.firstname, pgist_user_base.lastname, pgist_user.loginname FROM pgist_user_base, pgist_user WHERE pgist_user.id = pgist_user_base.id'
result = stmt.executeQuery(sql)

userIdList = []

while (result.next()):
	row = {}
	row['userId'] = result.getInt(1)
	row['name'] = result.getSting(2)
	row['lastname'] = result.getString(3)
	row['username'] = result.getString(4)
	userIdList.append(row)

result.close()
stmt.close()

stmt = connection.createStatement()
sql = 'SELECT author_id, content, numagree, numvote, replies FROM sarp_concerns WHERE deleted = FALSE'

result = smt.executeQuery(sql)

while (result.next()):
	['userId'] = result.getInt(1)
	['concern'] = result.getString(2)
	['numAgree'] = result.getInt(3)
	['numVote'] = result.getInt(4)
	['numReplies'] = result.getInt(5)


#connection.close()

print userIdList

#374-9439
#dental insurance