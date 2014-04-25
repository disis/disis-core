disis-core
==========

Distributed Simulation Infrastructure


Class header signature
----------------------

```java
/**
 * This is DISIS
 * Authors: Jirka Penzes & Jan Voracek
 * Date: ${DATE} ${TIME}
 */
```

Running Java RMI from IntelliJ IDEA
---------------------------------
1. Edit run configuration
2. Add VM option -Djava.rmi.server.codebase=file:PATH_TO_YOUR_PROJECT/out/production/RmiTest/

Note: Replace PATH_TO_YOUR_PROJECT with path to your project :)

Configuration sample
--------------------------
```json
{
    "local-name":"disis1",
    "local-port":"1099",
    
    "surrounding-services": [
        {
            "title":"DISIS 2", 
            "remote-name":"disis2",
            "network-address":"192.168.1.10",
            "port":"1090"
        },
        {
            "title":"DISIS 3", 
            "remote-name":"disis3",
            "network-address":"192.168.1.11",
            "port":"1090"
        },
        {
            "title":"DISIS 4", 
            "remote-name":"disis4",
            "network-address":"192.168.1.12",
            "port":"1090"
        }
    ]
}
```

The start command of DISIS: 

```
system:~ root$ disis configuration.json
```
