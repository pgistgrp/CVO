# Program to generate report.
# Input:
#     bctService: a database accessor for bct
#     cstService: a database accessor for cst
#     chtService: a database accessor for cht
#     vttService: a database accessor for vtt
#     drtService: a database accessor for drt
#     report : org.pgist.sarp.report.Report
#     output : a string, file path of the web pages for output

print "================= report object", report.id
print "================= output file", output

myfile = open(output,"w")
myfile.write("Content of the report!")
myfile.close()
