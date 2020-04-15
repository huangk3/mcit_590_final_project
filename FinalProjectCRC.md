# CRC

## DataReader

#### Responsibilities

* Import data from CSV file
* Parse data and store as patientClass

#### Methods

* Create self (Constructor) with filename input

#### Collaborators 

* patientClass
* DataAnalysis
* DisplayData

## patientClass

#### Responsibilities

* Store the pateint related metadata, clinical infomation
* Provide specific Patient information

#### Methods

* Getters

#### Collaborators 

* DataReader
* DataAnalysis
* DisplayData

## DataAnalysis

#### Responsibilities

* Handle missing data
* Calculate descritpive values 
* Generate Patient level aggregated measures of utilization (utilization profile)
* Compare utilization profile of two patients to calcualate 'similarity'
* Provide list of 'similar' member satisfying certain criteria

#### Methods

* describeData
* fillMissing
* generateUtilizationProfile
* similarityScore
* similarMemberList

#### Collaborators 

* patientClass

## DisplayData

#### Responsibilities

* Display Patient clinical profile
* Display utilization profile of given Patient
* Disaply comparision between two 'similar' patients

#### Methods

* showClinicalProfile
* showUtilizationProfile
* showSimilarPatientProfile

#### Collaborators 

* patientClass

* DataAnalysis

  

