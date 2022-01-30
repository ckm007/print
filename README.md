# Changes for MOSIP Release

* This document contains step by step details of the steps to be performed for the complete release of the MOSIP.
* As part of MOSIP release we push below mentioned components:
1. MOSIP artifactories to maven central under group `io.mosip`.
1. Push docker images  to `mosipid` dockerhub registry.
1. Tag the Codebase in respective repositories.
1. Sign the docker images released to mosipid dockerhub registry for trust validation.

Below are sequential steps for systematic release process.

## Pom changes in all the respective repositories.
* For releasig the artifacts to the maven central we needed to make sure that the versioning of all the artifactories are done properly so that the same can be used by other modules easily.
* After code freeze is announced and the MOSIP release is initiated first lock all the branches to be released in respective repositories and reserve the right to commit/release upto build and release team only and any further changes can't be merged without approval from DEV teams lead.
* For making the pom github mannual workflow named `release_changes` needed to be initiated manuallyfrom each repos in sequence the same is gicen here (repos.xls).
* After sucessfull manual workflow run a pull request will be raised to the repository to the release branch which contains all the versioning and URL changes for release.
* Merge the pull request created by release BOT.
* Once pull request is merged wait for all the automated actions to be completed sucessfully.

## Github workflow `push_trigger` 
* After sucessfull completion of github action make sure that:
1. All the jobs trigered by github `push_trigger` workflow completed sucessfully.  
1. In case of `build` job failures do revert the same to repository admins.
1. In case of `publish_to_nexus` job failure do update the same to Devops team.
1. In case of failure related to publishing docker images do update the same to Devops team. 

## NEXUS repository manager
1. Login to Nexus and verify if all the artifacts to be released from respective repos are there in staging repository in closed state without any validation failure.
1. If any artifactory repo is in open state do select the same and close it and wait for sucessful closing of the repos there.
1. If repos don't close sucessfully note the validation failures there nd make the required changes in the POM files in the repository and commit the changes.
1. After artifactory repositories sucessful closing from the staging repository in NEXUS do crosscheck if the version to be released for each artifacts is already not released in Maven central.
1. Once after ensuring that the version to be released is unique and is not present already in Maven Central release the repositories from NEXUS.
1. Wait until the arifactories released are shown under specified version in each group.
1. Trigger the release changes for the other dependent repos in sequence.

## Release docker images to `mosipid` dockerhub account
* Once after all the repos are released and all the images are published into the `mosidev` dockerhub organosation make note of them.
* Now execute the python [script](push/README.md) to push images from `mosipdev` to `mosipid` organisation.

## Docker image signing
* Once all the docker images are pushed to the `mosipid` account make sure that the same is signed properly using [this](sign/README.md) 

## Tagging and Release
* Once after sucessfull elease of artifactories and docker images continuw with tagging of all the repositories from where release was initiated with release version.

## Infrastructure release changes
* After sucessfull release of libraries and docker images we need to update the related changes in the below mentioned repos:
1. mosip-infra : update correct versions of all the docker images released in `version.yml` file for deployment.
1. mosip-helm : update the respective docker image versions there in all the required charts and publish the same.
1. mosip-artifactory-ref-impl: update the repective required libraries version in the artifactory-ref-impl so that artifactory servivce docker image can be prepared and published and signed for trust validation.
1. pvt-artifactory: update all the latest artifactory changes with the real sdk jars for [RBR](real biometric testing).

## Release testing
* Once all the above changes are done we need to verify the deployment once using both the refernce implememtation deployment architeture [sandboxv2](https://github.com/mosip/mosip-infra/tree/1.2.0-rc2/deployment/sandbox-v2) and [V3](https://github.com/mosip/mosip-infra/tree/1.2.0-rc2/deployment/v3#readme).

## Infrastructure related repos tagging and release
* After deployment testing tag and release the below mentioned repos:
1. mosip-artifactory-ref-impl: Contains code for artifactory service docker image for the MOSIP mosular deployment.
1. pvt-artifactory: Contains third party vendor jars and zips for MOSIP real device testing.
1. mosip-infra: Contains all deployment scripts.
1. mosip-helm: Contains all the MOSIP and required external charts for deployment.
1. mosip-config: Contains all the configurations required for the MOSIP modular services.
1. mosip-data: Contains all the necessary DML's required for MOSIP.

## Merging latest Codebase to master
* After complete release of MOSIP compones the merging of all the changes from release branch is done to the master. 

## Release Notes publish
* Once after tagging and release of all the Codebase and Infrastructure Release notes are published [here](path to release notes)

## Anouncement for Open source Community
* After Release Notes are published  anouncement of the official release of the MOSIP's new version in open source is made.
