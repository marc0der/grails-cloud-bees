# CloudBees Plugin

## Description

CloudBees provides Platform as a Service for Java that offers a complete end-to-end environment, from development to production. As a Java (and hence, Groovy & Grails) developer, you can more easily create quality software by putting your code into the DEV@cloud build system, and from there instantly publishing your application to the cloud using RUN@cloud for Java.

This operation requires interaction with the CloudBees API through their SDK. This plugin exposes the CloudBees SDK API through a series of command line scripts. The CloudBees SDK does not need to be installed on your box for this to work. Simply install the plugin and start invoking commands on the cloud.

## Installation

    grails install-plugin cloud-bees

## Application Configuration

Insert the following lines in your `grails-app/conf/Config.groovy` file:

    cloudbees.api.url='https://api.cloudbees.com/api'
    cloudbees.api.key='CLOUD_BEES_API_KEY'
    cloudbees.api.secret='CLOUD_BEES_API_SECRET'

Provide the API key and secret as required.

## Commands

All commands are issued prefixed by the grails command, or in the interactive shell. e.g

    grails bees-app-list

The following commands are provided:

    bees-app-checksums <appId>
    bees-app-delete <appId>
    bees-app-deploy <appId> <tag> <war>
    bees-app-info <appId>
    bees-app-list
    bees-app-restart <appId>
    bees-app-start <appId>
    bees-app-stop <appId>
    bees-app-tail <appId> LOGNAME

    bees-db-create <domain> <dbId> <username> <password> 
    bees-db-delete <dbId>
    bees-db-info <dbId>
    bees-db-list

All commands have comprehensive help, which can be viewed as follows:

    $ grails help bees-app-info
    …
    Environment set to development
    
    grails bees-app-info -- Returns the basic information about an application.
    
    Usage (optionals in square brackets):
    
    grails bees-app-info <appId> appId : the application id (in the form user/appname)
    
## Source Code

Source code can be found on GitHub at: https://github.com/marcoVermeulen/grails-cloud-bees