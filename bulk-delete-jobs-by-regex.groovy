/*** BEGIN META {
  "name" : "Bulk Delete Jobs by RegEx",
  "comment" : "Delete jobs by a RegEx pattern",
  "parameters" : [ 'dryRun', 'regexp' ],
  "core": "1.652",
  "authors" : [
    { name : "Steven Nemetz" }
  ]
} END META**/

import jenkins.model.*
import java.util.regex.Pattern
import java.util.Date

jenkins = Jenkins.instance

dryRun = dryRun.toBoolean()
// Set default to select nothing
regexp = regexp ?: '^$'
pattern = Pattern.compile(regexp)
//  Display parameters running with
println "Parameters running with:"
println "\tDry mode: ${dryRun}"
println "\tRegexp: ${regexp}"

int count = 0
if (dryRun) {
  msgJob = "Would delete"
} else {
  msgJob = "Deleting"
}
jobs = jenkins.items.findAll{job -> (job instanceof hudson.model.AbstractProject
          && pattern.matcher(job.name).matches()) }
jobs.each { job ->
  println "${msgJob} ${job.name}"
  if (!dryRun) {
    job.delete()
  }
  count++
}

if (dryRun) {
  msgTotal = "Would have deleted"
} else {
  msgTotal = "Deleted"
}
println "\n${msgTotal} ${count} jobs.\n"
