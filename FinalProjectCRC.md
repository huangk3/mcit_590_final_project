# CRC

## dataReader

#### Responsibilities

* Import data from CSV file
* Parse data and store as patientClass

#### Methods

* Create self (Constructor) with filename input

#### Collaborators 

* patientClass
* dataAnalysis
* display

## patientClass

#### Responsibilities

* Store the pateint related metadata, clinical infomation
* Provide specific patient information

#### Methods

* Getters

#### Collaborators 

* dataReader
* dataAnalysis
* display

## dataAnalysis

#### Responsibilities

* Handle missing data
* Calculate descritpive values 
* Generate patient level aggregated measures of utilization (utilization profile)
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

## display

#### Responsibilities

* Display patient clinical profile
* Display utilization profile of given patient
* Disaply comparision between two 'similar' patients

#### Methods

* showClinicalProfile
* showUtilizationProfile
* showSimilarPatientProfile

#### Collaborators 

* patientClass

* dataAnalysis

  

