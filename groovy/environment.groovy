// get current thread / Executor
def thr = Thread.currentThread()
// get current build
def build = thr?.executable

build_number = currentBuild.number
def resolver = build.buildVariableResolver
def gitTag = resolver.resolve("BRANCH_TAG_COMMIT")

def tagOrBranchName = gitTag
if ( gitTag.startsWith("refs/tags/") ){
	def trimmedRefs = gitTag.split('refs/tags/')
	tagOrBranchName = trimmedRefs[1]
}
if ( gitTag.startsWith("refs/heads/") ){
	def trimmedRefs = gitTag.split('refs/heads/')
	tagOrBranchName = trimmedRefs[1]
}
print tagOrBranchName
def tagValues = [tagOrBranchName,build_number]
if ( tagOrBranchName.contains("_JENKINS_BUILD_") ){
	tagValues = tagOrBranchName.split('_JENKINS_BUILD_')
        build_number = tagValues[1]
         if ( tagValues[1] .contains(".") ){
             //tagValues[1]  =  tagValues[1].split(".")[3]
            maj_min_pat = tagValues[1].split("\\.")[0] + "." + tagValues[1].split("\\.")[1] +"."+tagValues[1].split("\\.")[2]
            build_number =  tagValues[1].split("\\.")[3]
        }
}
def map = [JOB_BRANCH_NAME: tagValues[0],JOB_TO_BUILD_BUILD_NUMBER: build_number, TRIMMED_BRANCH_TAG_COMMIT:  tagOrBranchName, MAJOR_MINOR_PATCH: maj_min_pat]
