package org.devops


//构建类型
def Build(buildType,buildShell){
    def buildTools = ["mvn":"M2","ant":"ANT","gradle":"GRADLE","npm":"NPM"]
    
    
    println("当前选择的构建类型为 ${buildType}")
    buildHome= tool buildTools[buildType]
    
    if ("${buildType}" == "npm"){
        
        sh  """ 
            export NODE_HOME=/usr/local/src/build/node-v14.15.1-linux-x64
            export PATH=$PATH:/usr/local/src/build/node-v14.15.1-linux-x64/bin:/usr/local/src/build/yarn-v1.12.0/bin
            yarn config set cache-folder /usr/local/src/build/yarn-v1.12.0/cache                     
            /usr/local/src/build/node-v14.15.1-linux-x64/bin/npm i                    
            /usr/local/src/build/node-v14.15.1-linux-x64/bin/${buildType}  run  ${buildShell}
            """
    } else {
        sh """
        export JAVA_HOME=/usr/local/src/build/jdk-11.0.9
        export JRE_HOME=/usr/local/src/build/jdk-11.0.9/jre
        /usr/local/src/build/apache-maven-3.6.3/bin/${buildType} clean -Dmaven.test.skip=true package -P ${buildShell}
        """
    }
}
