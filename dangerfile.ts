import {danger, fail, warn} from 'danger'


// No PR is too small to include a description of why you made a change
if (danger.github.pr.body.length < 10) {
  fail('Please include a description of your PR changes.');
}

// Warn when there is a big PR
const bigPRThreshold = 500;
if (danger.github.pr.additions + danger.github.pr.deletions > bigPRThreshold) {
  warn(':exclamation: Big PR');
}

// Check for a CHANGELOG entry
const hasChangelog = danger.git.modified_files.some(f => f === 'CHANGELOG.md')
const description = danger.github.pr.body + danger.github.pr.title
const isTrivial = description.includes('#trivial')

if (!hasChangelog && !isTrivial) {
  warn('Please add a changelog entry for your changes.')
}

// Check if PR is opened again develop
const isMergeRefMaster = danger.github.pr.base.ref === 'master';
const isMergeRefDevelop = danger.github.pr.base.ref === 'develop';
if (!isMergeRefDevelop && isMergeRefMaster) {
  const title = ':grey_question: Base Branch';
  const idea =
    'The base branch for this PR is something other than `develop`. Are you sure you want to merge these changes into `master` ?';
  warn(`${title} - <i>${idea}</i>`);
} else if (!isMergeRefDevelop && !isMergeRefMaster) {
  const title = ':exclamation: Base Branch';
  const idea =
    'The base branch for this PR is something other than `develop`. [Are you sure you want to target something other than the `master` branch?](https://github.com/faberNovel/stateful-layout/blob/master/docs/CONTRIBUTING.md)';
  fail(`${title} - <i>${idea}</i>`);
}
