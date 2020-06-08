#!groovy​
JAVA_VERSION = "8.202"
MAVEN_VERSION = "3.6.0"


properties([[$class  : 'BuildDiscarderProperty',
             strategy: [$class      : 'LogRotator',
                        numToKeepStr: '10']]])

def pom
def branchName = "${env.BRANCH_NAME}"
def branchType = get_branch_type(branchName)

def branchDeploymentEnvironment
def snapshot = "-SNAPSHOT"

node('jnode') {
    stage('Checkout') {
        cleanWs()
        checkout scm
    }

    stage('Environment set') {
        pom = readMavenPom()
        def currentVersion = pom.version
        currentBuild.displayName = "${env.BRANCH_NAME}-${currentVersion} #${env.BUILD_NUMBER}"
    }

    stage('Clean') {
        mvn "clean"
    }

    if (branchType == "pull" || branchType == "dev") {
        stage('Compile & CheckStile') {
            mvn "-Dcheckstyle.excludes=\"**/mappers/*Impl.java\" compile"
        }

        stage('Test') {
            mvn "test -Dcheckstyle.skip"
        }

        stage('Sonar') {
            sonar("sonar", pom, branchType)
        }

        stage('Publish') {
            publish()
        }

        stage('Quality Gate') {
            qualityGate(1)
        }
    }
}

def get_branch_type(String branch_name) {
    print branch_name
    def dev_pattern = ".*develop"
    def release_pattern = ".*release/.*"
    def feature_pattern = ".*feature/.*"
    def hotfix_pattern = ".*hotfix/.*"
    def master_pattern = ".*master"
    def pull_pattern = ".*PR-.*"
    if (branch_name =~ dev_pattern) {
        return "dev"
    } else if (branch_name =~ release_pattern) {
        return "release"
    } else if (branch_name =~ master_pattern) {
        return "master"
    } else if (branch_name =~ feature_pattern) {
        return "feature"
    } else if (branch_name =~ hotfix_pattern) {
        return "hotfix"
    } else if (branch_name =~ pull_pattern) {
        return "pull"
    } else {
        return null;
    }
}

def mvn(String goals) {
    withMaven(
            jdk: JAVA_VERSION,
            maven: MAVEN_VERSION) {
        sh "mvn -B ${goals}"
    }
}

def sonar(String goals, pom, branch_type) {
    withMaven(
            jdk: JAVA_VERSION,
            maven: MAVEN_VERSION) {
        withSonarQubeEnv {
            sh "mvn sonar:${goals} " +
                    "-Dsonar.projectKey='${pom.groupId}:${pom.artifactId}:${branch_type}' " +
                    "-Dsonar.projectName='${pom.name}:${branch_type}' " +
                    "-Dsonar.java.checkstyle.reportPaths='target/checkstyle-result.xml' " +
                    "-Dsonar.links.ci='${JENKINS_URL}' " +
                    "-Dsonar.jacoco.reportPath='target/jacoco.exec' " +
                    "-Dsonar.coverage.exclusions=**com/scalefocus/java/plexnikolaypetrov/configurations/*.java," +
                    "**com/scalefocus/java/plexnikolaypetrov/dtos/*.java," +
                    "**com/scalefocus/java/plexnikolaypetrov/utils/constants/*.java," +
                    "**com/scalefocus/java/plexnikolaypetrov/entities/*/*.java," +
                    "**com/scalefocus/java/plexnikolaypetrov/filters/*.java," +
                    "**com/scalefocus/java/plexnikolaypetrov/mappers/*.java," +
                    "**com/scalefocus/java/plexnikolaypetrov/models/*.java," +
                    "**com/scalefocus/java/plexnikolaypetrov/enums/*.java"
        }
    }
}

def publish() {
    findbugs canComputeNew: false, canRunOnFailed: true, defaultEncoding: '', excludePattern: '**/mappers/*Impl.java', failedTotalAll: '0', healthy: '', includePattern: '', pattern: '**/findbugs-result.xml', unHealthy: '', unstableTotalAll: '0'
    checkstyle canComputeNew: false, canRunOnFailed: true, defaultEncoding: '', failedTotalAll: '0', healthy: '', pattern: '**/checkstyle-result.xml', unHealthy: '', unstableTotalAll: '0'
    junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml'
    dependencyCheck additionalArguments: '', odcInstallation: '5.3.2'
    dependencyCheckPublisher failedNewCritical: 0, failedNewHigh: 0, failedNewLow: 20, failedNewMedium: 5, failedTotalCritical: 0, failedTotalHigh: 0, failedTotalLow: 20, failedTotalMedium: 5, pattern: 'dependency-check-report.xml', unstableNewCritical: 0, unstableNewHigh: 0, unstableNewLow: 10, unstableNewMedium: 3, unstableTotalCritical: 0, unstableTotalHigh: 0, unstableTotalLow: 10, unstableTotalMedium: 7
}

def qualityGate(timeOut) {
    timeout(time: timeOut, unit: 'HOURS') {
        def qg = waitForQualityGate()
        if (qg.status != 'OK') {
            error "Pipeline aborted due to quality gate failure: ${qg.status}"
        }
    }
}
