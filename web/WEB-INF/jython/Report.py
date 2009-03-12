# Program to generate report.
# Input:
#     report : org.pgist.sarp.report.Report
#     output : a string, file path of the web pages for output

print "================= report object", report.id
print "================= output file", output

myfile = open(output,"w")
myfile.write("Content of the report!")
myfile.close()
