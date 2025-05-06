# Cultural Heritage Data Management System 🏛️

This project is an academic initiative designed for managing cultural heritage data. It includes both a server and client component along with a command-based interface for searching and managing cultural heritage entries.

## 📋 Requirements

- **Java 17+**
- **Maven 3.6+**
- Data file: `Archeologia---Storia-e-Tradizioni---Regione-Marche.csv`
- All text files must be encoded in **UTF-8**

## 🚀 Installation

Clone the repository :

# Clone repository
git clone https://github.com/LaifMin/ProgettoServerClient_ArcheologiaRegioneMarche.git


## ⌨️ Command Reference
 Basic Commands   Command	Description
                 
HELP	            Show command reference
END              	Disconnect from server

Search Operations
Search commands follow this syntax:


SEARCH [OPTION] "QUERY"
Option	Search Type	Example
-n	Exact name	SEARCH -n "Castelldimezzo"

-c	Exact city	SEARCH -c Ancona

-t	Exact type	SEARCH -t Archeologia

-a	Exact address	SEARCH -a "Via Ferretti"

--n	Partial name	SEARCH --n castle

--c	Partial city	SEARCH --c piceno

--t	Partial type	SEARCH --t paesaggio

--a	Partial address	SEARCH --a piazza

-o	Opening days	SEARCH -o lunedì

## 📊 Data Format
The system expects data in the following format:
ImageURL;Caption;Type;Name;City;Address;Number;Phone;Email;Website;Latitude;Longitude;OpeningHours
