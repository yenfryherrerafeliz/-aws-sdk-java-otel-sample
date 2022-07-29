# aws-sdk-java-otel-sample

This sample application shows how to use the OpenTelemetry Java API and SDK to instrument the following calls from the AWS Java SDK V2.2:
- List Buckets on S3 Client
- List Tables on DynamoDB Client

This example exports span data to the console. I have also created a customization that sends data to mysql tables, and then from quicksight I pull that data to create some interesting analizys about the metrics gathered from OpenTelemetry.

# Prerequisites

Please complete the following tasks:
- Install Java JDK 8+ (Prefferable Java JDK 11) by following the steps found [here](https://docs.oracle.com/en/java/javase/18/install/overview-jdk-installation.html#GUID-8677A77F-231A-40F7-98B9-1FD0B48C346A)
- Install Maven by following the steps found [here](https://maven.apache.org/install.html)
- If you do not have a AWS account, please [create one](https://aws.amazon.com/premiumsupport/knowledge-center/create-and-activate-aws-account/)
- Setup [AWS credentials](https://docs.aws.amazon.com/cli/latest/userguide/cli-configure-files.html)
- Clone this repository by using git:
    - Fork this repo
    - And then, git clone git@github.com:YOURGITHUBUSERNAME/aws-sdk-java-otel-sample.git
- If do not have git installed you can download the project as a zip file:
    - [Download project as zip file](https://github.com/yenfryherrerafeliz/aws-sdk-java-otel-sample/archive/refs/heads/main.zip)

As suggestion you could try [SDKMAN](https://sdkman.io/) which is a great tool for managing parallel versions of multiple SDKs on must unix based systems.

# Setup

The test code of this sample application uses the opentelemetry instrumentation libraries for aws. More details can be found in the reference section.

- Go to the root directory for this project, which will be where a pom.xml file is located
- Then, run [mvn dependency:resolve](https://#) to resolve the dependencies used on this project
- Then, run [mvn clean](https://#) to clean the target folder where we will be generating our jar application
- Then, run [mvn package](https://#) to generate our final jar

# Running the sample application

From the root directory of this project run:
- java -jar target/ws-sdk-java-otel.jar

And the command above produces the following output:

```console
{
        TraceId: 915b458ee4283719a942cc918abba81b,
        SpanId: 23eb055cf2239f24,
        Name: DynamoDb.ListTables,
        ParentId: 0000000000000000,
        Kind: CLIENT,
        TimeStamp: 2022-07-28T09:51:13.010689,
        Duration: 886 milliseconds,
        Attributes: {
                net.transport: ip_tcp,
                http.flavor: 1.1,
                rpc.service: DynamoDb,
                net.peer.port: 443,
                http.url: https://dynamodb.us-east-2.amazonaws.com/,
                db.operation: ListTables,
                rpc.system: aws-api,
                net.peer.name: dynamodb.us-east-2.amazonaws.com,
                rpc.method: ListTables,
                db.system: dynamodb,
                http.user_agent: aws-sdk-java/2.17.209 Mac_OS_X/12.3.1 Java_HotSpot_TM__64-Bit_Server_VM/18.0.2+9-61 Java/18.0.2 kotlin vendor/Oracle_Corporation io/sync http/Apache cfg/retry-mode/legacy,
                http.status_code: 200,
                http.method: POST,
        }, 
        Resource: {
                service.name: aws-java-sdk-otel-sample,
                telemetry.sdk.language: java,
                telemetry.sdk.name: opentelemetry,
                telemetry.sdk.version: 1.15.0,
        },
        Events: {
        },
        Links: {
        },
}

```

# Export to MySQL
This sample application also allows you to export to mysql. To do this just do the following:
- Search for the script to create the tables found in src/main/resources/db_scripts/otel_traces.sql
- Load the script into your database to create the tables.
- Then, when running the jar please just provide the following variables
    - OTEL_EXP_MYSQL_HOST: the mysql host to connect
    - OTEL_EXP_MYSQL_PORT: the mysql port to connect
    - OTEL_EXP_MYSQL_USER: the mysql user to use for the connection
    - OTEL_EXP_MYSQL_PASSWORD: the mysql password
    - OTEL_EXP_MYSQL_DB_NAME: the database name
    - OTEL_EXP_DEFAULT=mysql: to use the mysql exporter

Here is a quick illustration from QuickSight:
<img width="1440" alt="Screen Shot 2022-07-28 at 11 31 41 PM" src="https://user-images.githubusercontent.com/47982775/181697702-2df52e82-feac-4e75-ba46-fd30901f2e03.png">

# References:
- [GitHub repo](https://github.com/open-telemetry/opentelemetry-java-instrumentation/tree/main/instrumentation/aws-sdk/aws-sdk-2.2) OpenTelemetry Java Instrumentation for AWS SDK V2.2
- [GitHub repo](https://github.com/open-telemetry/opentelemetry-java-instrumentation) OpenTelemetry Java Instrumentation
