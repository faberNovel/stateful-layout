# Sometimes it"s a README fix, or something like that - which isn"t relevant for
# including in a project"s CHANGELOG for example
DECLARED_TRIVIAL = github.pr_title.include? "#trivial"
BIG_PR_THRESHOLD = 500
HAS_CHANGELOG = git.modified_files.include? "CHANGELOG.md"
MERGE_ON_MASTER = github.branch_for_base == "master"
MERGE_ON_DEVELOP = github.branch_for_base == "develop"

# Make it more obvious that a PR is a work in progress and shouldn"t be merged yet
warn("PR is classed as Work in Progress") if github.pr_title.include? "[WIP]"

fail("Please include a description of your PR changes.") if (github.pr_body.length < 10)

# Warn when there is a big PR
if git.insertions + git.deletions > BIG_PR_THRESHOLD
    warn(":exclamation: Big PR") if git.lines_of_code > 500
end

# Check for a CHANGELOG entry
if !HAS_CHANGELOG && !DECLARED_TRIVIAL
    warn("Please add a changelog entry for your changes.")
end

# Check if PR is opened again develop
title=""
idea=""
if !MERGE_ON_DEVELOP && MERGE_ON_MASTER
    title = ":grey_question: Base Branch"
    idea =  "The base branch for this PR is something other than `develop`. Are you sure you want to merge these changes into `master` ?"
    warn("#{title} - <i>#{idea}</i>")
elsif !MERGE_ON_DEVELOP && !MERGE_ON_MASTER
    title = ":exclamation: Base Branch"
    idea = "The base branch for this PR is something other than `develop`. [Pull requests need to be against `develop`](https://github.com/faberNovel/stateful-layout/blob/master/docs/CONTRIBUTING.md)"
    fail("#{title} - <i>#{idea}</i>")
end
