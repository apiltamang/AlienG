
This is a JavaEE project, and an Apache Tomcat Container is used to run the web-application.

This document is primarily aimed at the personnel setting up the environment for this application
such as myself, with basic information on setting up Eclipse projects, MySQL Server etc. I am not
familiar with the domain itself.
//---------------------------------------------------------------------------------

Getting the java source code for this project

It can be had in two ways:

1) Highly recommended: Get admin access to VM located on ECU cluster @ ip: 150.216.54.56.
	All the databases, files and folders are ready for go...

2) Clone and build everything from scratch. Expect to take about a month to set everything up
   and running. Highly encouraged to follow the steps outlined below. Get the source code (only)
   from the public Git Repo: https://github.com/apiltamang/AlienG.git
//---------------------------------------------------------------------------------
This project needs the following databases to work with:

a) The NCBI taxonomy database (hereby referred to as the "taxonomy database".

b) The Genbank nr database (hereby referred to as the "blast database").

This project needs the following software environments to work

a) Java SDK 1.6 or higer
b) Eclipse for JavaEE developers (Keplar, 64-bit)
c) MySQL server
d) Database visualization interface (MySQL Workbench/PHPMyAdmin etc) <--Optional
e) The blast+ executables
//---------------------------------------------------------------------------------

TAX_DB_PATH:        C:\AlienG\taxonomy_database
BLAST_DB_PATH: 	    C:\AlienG\blast_database\
PYTHON_SOLVER_PATH: C:\AlienG


//--------------------------------------------------------------------------------
Steps to acquiring and setting the databases

1.  Setting up the taxonomy database
	> Open browser
	> type ftp://ftp.ncbi.nih.gov/blast/db/ and download file taxdb.tar.gz
	> type ftp://ftp.ncbi.nig.gov/pub/taxonomy/ and download file taxdump.tar.gz
	> Extract the content of these files into a folder. This is done in C:\taxonomy_database\ in 
	  this computer. A copy of this is also made in the folder: C:\AlienG
	  
2.  Setting up the MySQL database and corresponding data for this project.
    > The scrips for doing this are situated in a folder called "sql-scripts-for-initializing-database"
      This folder is within TAX_DB_PATH in this computer. Simply open the
      mysql terminal and run these sql scripts, following steps 1 through 5.

      Description: Steps 1-5 reads the files in TAX_DB_PATH, formats it in an appropriate way, and
      then populates the tables in the database that you choose. Right now, it populates into a 
      database called "alieng_db".
    > make sure that you run an additional script with extension *.step6.sql as well to complete this phase
      of the setup. It is indispensable that you do this. This formats the contents additonally, and also
      builds an index for each table, especially the table "name_to_id". Leads to a big performance gain.
      
      
3.  Setting up the Genbank nr database
    > Open browser and type: ftp://ftp.ncbi.nih.gov/blast/db/
    > download all the files that has name: nr.##.tar.gz, where
      ## is a number ranging from 00 -- > NN
    > extract all these files into a folder. This is done in folder: 
      c in this computer.
    > combine the above files into one file. If you're using linux shell you can
      do this by typing: cat *.* > nr, where "nr" is the destination file. In this
      computer, I simply copied this file "nr" from a different source. The concantenated
      file has been placed in the following directory: C:\unformatted_combined_blast_database\
    > Now open the terminal in windows, and navigate to the blast+ executables. In this 
      computer, it is located in C:\Program Files\NCBI\blast-2.2.28+\bin\
    > Run the "makeblastdb" command as follows:
      makeblastdb -in C:\unformatted_combined_blast_database\nr -dbtype prot 
                  -out C:\formatted_blast_database\nr
      (This command formats the blast database so that you can run blastp command on it)

      NOTE: Installing the blast+ executables from a windows installer should let you execute one of
            the blast executables directly from the command line. For that, open your command line, 
	    and type: makeblastdb [Press Enter]. There should be an error other than 'Not recognized 
            as an internal or external command...'
      
    > Now move all the files ( the combined massive file called 'nr', and all the files created by 
      running the makeblastdb command, *.pin, *.psr, *.sog etc) into the folder
      BLAST_DB_PATH. I deleted the unformatted piecewise files downloaded at the beginning to save hard drive space.
      
      

    
//--------------------------------------------------------------------------------- 
    
Setting up the software environments:

The installation of the jdk (Java SDK ) for various platforms, including Eclipse for JavaEE developers
and MySQL abound on the internet. 

>>>>>>>> Setting up blast+ executables:

> Visit ftp://ftp.ncbi.nlm.nih.gov/blast/executables/blast+
> Download installer blast-2.2.28+ for this specific project
> Install following instructions. In this computer, the executables are installed in the folder
  C:\Program Files\NCBI\blast-2.2.28+\bin\

>>>>>>>> Setting up the JavaEE project

This application is supported by a web architecture that enables users to remotely upload input
files for the solution process. This application is also designed to run in an Apache Tomcat servlet 
container. Given the source code files, do the following to run the project...

1. Import the project into your eclipse's local workspace directory. 
   > In eclipse's main menu, click import>project
   > Navigate to source code folder
   > Make sure you copy the project into local workspace directory ( not necessary but advisable)
   > Then setup an apache tomcat server (follow instructions on internet about setting up libraries)
   > Attempt to run the project by
     > Right click project > Run As > Run on Server ..
     > Resolve any library dependencies
     
2. Configure access points for MySQL Server
   > Navigate to Thesis > src > al.ali.mysql.MySQLAccess.java and make changes. In particular
   a) In method newConnection, make sure you're using the right name of the database 
      ("alieng_db_taxonomy_updated” for this computer)
   b) configure port and host access tokens

3. Make sure that the folders C:\AlienG, and C:\AlienG\blast_database exist in the computer.

4. Make sure you have admin. rights to this computer. This is because you will be doing a lot
   of read/write operations to the root drive.

5. type http://localhost:80/Thesis/ in a browser to start placing orders.

6. you can navigate the database and the tables using the link: http://localhost:70/phpmyadmin/index.php

7. IMPORTANT:   It is not sufficient to just get the server running to start solving requests.
		You should also get the java application to start running. This will be
		the file:

		al.ali.main.Interface.java

		Navigate to this file, make sure it contains the entry point of the application:
		public static void main(String[] args){

		. . . .
		. . .
                }

		Right-click, and click “Run As”>”Java Application”

		What this does is get the application running so that it periodically checks
		for new requests in the “Orders” table, and if it finds one, invokes the
		python solvers to solve the problem.

#------------------------------------------------------------------------------------
Setting up the Python AlienG Environment.

You need to have the following softwares installed:
[In the order of installation]
- Python ver 2.x (preferrably 2.6)
- Numpy for 2.6
- A compatible BIOPYTHON package

I used Enthought Python Canopy installer to install a bundled Python environment. This is
available for free for academic use. Be sure to get the one where the sceintific packages
are included, as it is crucial to have BIOPYTHON package in your python environment.

The source for the AlienG solver  is located in the folder: PYTHON_SOLVER_PATH (See at the
beginning of the document). The folder contains the following files and folders:

1) *.py  -These are the core python modules that constitutes the algorithm.
2) *.pyc -These are compiled python modules generated by the python interpreters. Not of our utility or interest.

3) blast_database 
         -This folder contains the "nr" file created above, including all other files created by running the 
          makeblastdb executable on the "nr" file. It is extremely important to keep it intact.
4) taxonomy_database
         -This folder contains the taxonomy database files + sql scripts to initialize the mysql database. Also 
          critical for the project.
5) taxonomy_mappings_backup
         -This folder is also critical for running any AlienG jobs. As you will see later, the contents of this 
          folder are created by running one of the python code on the taxonomy files. Until and unless the contents
          of the taxonomy_database folders change (because of updating those files, or using a different database)
          you shouldn't have to bother with these files.
6) test1.xml and test2.xml
         -These files are provided to you as test input files for running the Python code. The corresponding helper
	  files are conf1.conf and conf2.conf which are also required for running either test1 or test2. The xml files
	  were created by running ‘blast’ or ‘blastp’ on certain query sequences. The JAVA version of this analysis,
	  which you will see in the Eclipse Project, aimed to include this execution in the solution, but it needed
	  to be quickly dismissed. This is because this execution can take weeks to finish in a multi-core, HPC 
	  cluster, and would inundate this VM’s CPU/memory resources if we tried to run it here.
7) conf1.conf and conf2.conf
	-These files contain parameters to consider for running the AlienG analysis. Please consider looking at the
	 original manual that was written for the PYTHON code to understand what each of the parameter means.

           
8) taxonomy_mappings_test1 and taxonomy_mappings_test2
	-These files are required by the Python code to run test1 and test2 respectively. In order for you to run either 
	 of these tests (test1 or test2), copy all the files from the folder ‘taxonomy_mappings_backup’ to the corresponding
	 taxonomy_mappings_test# folder. You NEED to do this each time you run the test from the start. This is because the
	 code will write changes back to this file when in execution, thus the files will be corrupt.

9) helpful_documentation
	
	This folder contains some helpful documenting materials. They are

	a)AlienG_Project_Report.pdf
		-This is the original manual for the code and algorithm written by the original author back in 2009.
	b)AliAhmadvand_Thesis_Version5.docs
		-This is the thesis of the student who wrote the Java version of the AlienG. Some bugs fixed.

In order to run one of the tests (say, test1), do the following:

	a) Copy all the files from folder: taxonomy_mappings_backup, to folder: taxonomy_mappings_test1
	
	b) Open a command terminal, navigate to folder C:\AlienG, then enter following in the command prompt:
	
	   C:\AlienG>python interface.py -c conf1.conf -x

NOTE:
if you’ve modified the files in taxonomy_database folder as a result of updating the files OR, you need to use a different 
taxonomy_database, then you need to run the following python command:

	< WARNING >
	The following commands will write the formatted taxonomy mappings file to folder: “taxonomy_mappings_test1”.
	Before you let the command execute, you MUST create a couple of empty files and place it in this folder. This
	forces the program to use a certain implementation of the python’s “Shelve” module, which will be critical
	later on for solving the problem. It was found that

	- if the above approach is not followed, then the execution time goes up dramatically, and 
	- there is also some disparity in the solution. We don’t want this, so please do as stated above.

	The empty files to create and place in the folder: “taxonomy_mappings_test1” are
	- id_to_node.data.dat
	- id_to_node.data.dir
	- id_to_name.data.dat
	- id_to_name.data.dir
	- name_to_id.data.dir
	- name_to_id.data.dat

	NOTE:
	- You will also have to do the above in a local computer (i.e. not in this VM). For that, you only
	  need the taxonomy_database folder in your local computer. Then follow the steps below to create the
	  mapping files. Later, find a way to upload these files to the corresponding folder in the VM.

	a)In the command terminal, enter:
	
	 C:\AlienG>python interface.py -c conf1.conf -t

	This will read the files in the taxonomy_database folder, format it appropriately, and write it back to the 
	folder: “taxonomy_mappings_test1”. Copy all the files from this directory to the folder: “taxonomy_mappings_backup”
	since this will now be the backup file used for all subsequent analysis.

ADVICE:

Again, please consider reading the report on this project as originally written by the author for additional
information.
#------------------------------------------------------------------------------------



     


		

