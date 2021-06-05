# Apothecary Schnabel  
[![Build Status](https://travis-ci.com/SchnabelORG/schnabel.svg?branch=develop)](https://travis-ci.com/SchnabelORG/schnabel)
Internet Software Architectures class project

Centralized Pharmacy Information System allowing users to acquire medical supplies  
and recieve professional consulting from pharmacists and dermatologists.

## Table of Contents
- [Requirements](#requirements)
- [Setup Guide](#setup-guide)
  * [Linux / Mac](#linux---mac)
    + [Ubuntu](#ubuntu)
    + [Arch](#arch)
    + [Mac](#mac)
  * [Windows](#windows)
- [Members and functionalities](#members-and-functionalities)
  * [Optional functionalities](#optional-functionalities)

## Requirements
- JDK 11 or newer
- Node.js 14.0.0
- Maven

## Setup Guide

### Linux / Mac
Setup above listed requirements depending on your OS/distro
#### Ubuntu

**JDK 11**  
```bash
# Update repositories and install JDK11
sudo apt update
sudo apt install openjdk-11-jre-headless
```

**Node**  
For Node.js it is recommended to use [nvm](https://github.com/nvm-sh/nvm)  
Once nvm is installed, do the following:
```bash
# Install node 14.0.0 and set it as current default
nvm install 14.0.0
nvm use 14.0.0
```

**Maven**  
```bash
# Update repositories and install Maven
sudo apt update
sudo apt install maven

# Verify installation
mvn -version
```

After installing the necessary packages, clone this repo, cd to /src/main/frontent/ and run: 
```bash
npm install
```

#### Arch

**JDK 11**
```bash
# Update repositories and install JDK11
sudo pacman -Syyu jdk11-openjdk
```

**Node**  
For Node.js it is recommended to use [nvm](https://wiki.archlinux.org/index.php/Node.js#Alternate_installations)  
Once nvm is installed, do the following:
```bash
# Install node 14.0.0 and set it as current default
nvm install 14.0.0
nvm use 14.0.0
```

**Maven**
```bash
# Update repositories and install Maven
sudo pacman -Syyu maven

# Verify installation
mvn -version
```

After installing the necessary packages, clone this repo, cd to /src/main/frontent/ and run: 
```bash
npm install
```

#### Mac

**JDK 11**  
Follow this well written guide on [medium](https://medium.com/w-logs/installing-java-11-on-macos-with-homebrew-7f73c1e9fadf)

**Node**  
For Node.js it is recommended to use [nvm](https://jamesauble.medium.com/install-nvm-on-mac-with-brew-adb921fb92cc)  
Once nvm is installed, do the following:
```bash
# Install node 14.0.0 and set it as current default
nvm install 14.0.0
nvm use 14.0.0
```

**Maven**  
Follow the Official Apache [tutorial](https://maven.apache.org/install.html)  

After installing the necessary packages, clone this repo, cd to /src/main/frontent/ and run: 
```bash
npm install
```

### Windows

**JDK11**  
Download and install the Official [installer](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)  

**Node**  
For Node.js it is recommended to use [nvm-windows](https://github.com/coreybutler/nvm-windows)  
Once nvm-windows is installed, close any opened CMD's and open a new one, then:  
```bash
# Install node 14.0.0 and set it as current default
nvm install 14.0.0
nvm use 14.0.0
```

**Maven**  
Download and install from the Official [website](https://maven.apache.org/install.html)  

After installing the necessary packages, clone this repo, cd to /src/main/frontent/ and run: 
```bash
npm install
```

## Members and functionalities

Basic functionalities required of all members.
Status values:
- ✓ - Functionality implemented
- ✗ - Functionality not yet implemented

**Jovan Ivošević**

| Spec. id | Functionality                                     | Status |
|----------|---------------------------------------------------|:------:|
| 3.1      | Unauthenticated user view                         |   ✓    |
| 3.3      | User profile                                      |   ✗    |
| 3.9      | User homepage                                     |   ✗    |
| 3.13     | Dermatologist appointment scheduling as patient   |   ✓    |
| 3.15     | Cancelling dermatologist appointment              |   ✓    |
| 3.16     | Pharmacological consultation scheduling           |   ✓    |
| 3.18     | Cancelling pharmacological consultation           |   ✓    |
| 3.19     | Medical drug reservation                          |   ✓    |
| 3.20     | Cancelling drug reservation                       |   ✓    |
| 3.31     | Pharmacy search and filtering                     |   ✓    |
| 3.41     | Rating system                                     |   ✓    |

**Kristian Farkaš**

| Spec. id | Functionality                                     | Status |
|----------|---------------------------------------------------|:------:|
| 3.4      | Pharmacy admin profile                            |   ✗    |
| 3.8      | Pharmacy profile                                  |   ✗    |
| 3.12     | Free dermatological appointment creation as admin |   ✗    |
| 3.22     | Discounts and special offers creation             |   ✗    |
| 3.24     | Order form creation as admin                      |   ✗    |
| 3.26     | Order form offer selection as admin               |   ✗    |
| 3.29     | Price list creation and update                    |   ✗    |
| 3.32     | Dermatologist search and filtering                |   ✗    |
| 3.33     | Pharmacist search and filtering                   |   ✗    |
| 3.38     | Accept or reject vacation or paid leave request   |   ✗    |

**Radovan Župunski**

| Spec. id | Functionality                                     | Status |
|----------|---------------------------------------------------|:------:|
| 3.5      | Pharmacist / Dermatologist profile                |   ✗    |
| 3.10     | Dermatologist homepage                            |   ✗    |
| 3.11     | Pharmacist homepage                               |   ✗    |
| 3.14     | Dermatological appointment scheduling as derm.    |   ✗    |
| 3.17     | Pharmacological consultation scheduling as derm.  |   ✗    |
| 3.21     | Handing out and acquiring medical drug            |   ✗    |
| 3.27     | Derm. appointment summary                         |   ✗    |
| 3.28     | Pharm. appointment summary                        |   ✗    |
| 3.30     | User search                                       |   ✗    |
| 3.36     | Pharmacist calendar display                       |   ✗    |
| 3.37     | Dermatologist calendar display                    |   ✗    |

**Marko Pekez**

| Spec. id | Functionality                                     | Status |
|----------|---------------------------------------------------|:------:|
| 3.2      | User registration and log in                      |   ✗    |
| 3.6      | Admin profile                                     |   ✗    |
| 3.7      | Supplier profile                                  |   ✗    |
| 3.23     | Discount and special offer subscription           |   ✗    |
| 3.25     | Order form offer provision as supplier            |   ✗    |
| 3.34     | Drug search and filtering                         |   ✗    |
| 3.35     | eReceipt (QR) drug search and hand out            |   ✗    |
| 3.39     | Complaint creation and response                   |   ✗    |
| 3.40     | Loyalty program creation                          |   ✗    |
| 3.42     | Drug and eReceipt specification                   |   ✗    |


### Optional functionalities  

**Jovan Ivošević**

| Spec. id | Functionality                                     | Status |
|----------|---------------------------------------------------|:------:|
|    /     | Grading system with penalization                  |   ✗    |
| 5.4      | Concurrent database access                        |   ✗    |
| 5.7      | DevOps (micro) flow                               |   ✗    |
|    /     | 5 unit and integrational test                     |   ✗    |
| 5.8      | Scalability                                       |   ✗    |

**Kristian Farkaš**

| Spec. id | Functionality                                     | Status |
|----------|---------------------------------------------------|:------:|
|    /     | Maps and business report display                  |   ✗    |
| 5.4      | Concurrent database access                        |   ✗    |
| 5.7      | DevOps (micro) flow                               |   ✗    |
|    /     | 5 unit and integrational test                     |   ✗    |
| 5.8      | Scalability                                       |   ✗    |

**Radovan Župunski**

| Spec. id | Functionality                                     | Status |
|----------|---------------------------------------------------|:------:|
|    /     | Calendar display and drug handout / acquisition   |   ✗    |
| 5.4      | Concurrent database access                        |   ✗    |
| 5.7      | DevOps (micro) flow                               |   ✗    |
|    /     | 5 unit and integrational test                     |   ✗    |
| 5.8      | Scalability                                       |   ✗    |

**Marko Pekez**

| Spec. id | Functionality                                     | Status |
|----------|---------------------------------------------------|:------:|
|    /     | eReceipt serach and handout; loyalty program def. |   ✗    |
| 5.4      | Concurrent database access                        |   ✗    |
| 5.7      | DevOps (micro) flow                               |   ✗    |
|    /     | 5 unit and integrational test                     |   ✗    |
| 5.8      | Scalability                                       |   ✗    |
