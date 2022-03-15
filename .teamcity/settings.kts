import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.projectFeatures.buildReportTab
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.vcs
import jetbrains.buildServer.configs.kotlin.v2019_2.vcs.GitVcsRoot

/*
The settings script is an entry point for defining a TeamCity
project hierarchy. The script should contain a single call to the
project() function with a Project instance or an init function as
an argument.

VcsRoots, BuildTypes, Templates, and subprojects can be
registered inside the project using the vcsRoot(), buildType(),
template(), and subProject() methods respectively.

To debug settings scripts in command-line, run the

    mvnDebug org.jetbrains.teamcity:teamcity-configs-maven-plugin:generate

command and attach your debugger to the port 8000.

To debug in IntelliJ Idea, open the 'Maven Projects' tool window (View
-> Tool Windows -> Maven Projects), find the generate task node
(Plugins -> teamcity-configs -> teamcity-configs:generate), the
'Debug' option is available in the context menu for the task.
*/

version = "2021.2"

project {
    description = "Contains all other projects"

    features {
        buildReportTab {
            id = "PROJECT_EXT_1"
            title = "Code Coverage"
            startPage = "coverage.zip!index.html"
        }
    }

    cleanup {
        baseRule {
            preventDependencyCleanup = false
        }
    }

    subProject(MachineSetup)
}


object MachineSetup : Project({
    name = "Machine Setup"

    vcsRoot(MachineSetup_HttpsGithubComJayBazuziMachineSetupRefsHeadsMain)

    buildType(MachineSetup_Build)
})

object MachineSetup_Build : BuildType({
    name = "Build"

    vcs {
        root(MachineSetup_HttpsGithubComJayBazuziMachineSetupRefsHeadsMain)
    }

    triggers {
        vcs {
        }
    }
})

object MachineSetup_HttpsGithubComJayBazuziMachineSetupRefsHeadsMain : GitVcsRoot({
    name = "https://github.com/JayBazuzi/machine-setup#refs/heads/main"
    url = "https://github.com/JayBazuzi/machine-setup"
    branch = "refs/heads/main"
    branchSpec = "refs/heads/*"
    authMethod = password {
        userName = "jaybazuzi"
        password = "credentialsJSON:79666d3b-6a19-49ed-bddf-6eab55b15147"
    }
})
