package com.fangcheng.javalabs.fatjar;

import java.net.URL;

public class SDKClient {

    public static  String getSDKPatch () {

    //
        Class klass = SDKClient.class;
        URL myLocation = SDKClient.class.getResource('/' + klass.getName().replace('.', '/') + ".class");

        System.out.println("URL" + myLocation);


        URL jarLocation = klass.getProtectionDomain().getCodeSource().getLocation();

        System.out.println("jarURL" + jarLocation);

        String jarPath  = jarLocation.getPath();

        System.out.println("jarPath" + jarPath);


        // String path =  myLocation.getPath();

      //  System.out.println("path" + path);

        String url =  SDKClient.class.getResource("/").toString();

        return  url;

    }


    /**
     * Compute the absolute file path to the jar file.
     * The framework is based on http://stackoverflow.com/a/12733172/1614775
     * But that gets it right for only one of the four cases.
     *
     * @param aclass A class residing in the required jar.
     *
     * @return A File object for the directory in which the jar file resides.
     * During testing with NetBeans, the result is ./build/classes/,
     * which is the directory containing what will be in the jar.
     */
    public static String getJarDir(Class aclass) {
        URL url;
        String extURL;      //  url.toExternalForm();

        // get an url
        try {
            url = aclass.getProtectionDomain().getCodeSource().getLocation();
            // url is in one of two forms
            //        ./build/classes/   NetBeans test
            //        jardir/JarName.jar  froma jar
        } catch (SecurityException ex) {
            url = aclass.getResource(aclass.getSimpleName() + ".class");
            // url is in one of two forms, both ending "/com/physpics/tools/ui/PropNode.class"
            //          file:/U:/Fred/java/Tools/UI/build/classes
            //          jar:file:/U:/Fred/java/Tools/UI/dist/UI.jar!
        }

        // convert to external form
        extURL = url.toExternalForm();

        // prune for various cases
        if (extURL.endsWith(".jar"))   // from getCodeSource
            extURL = extURL.substring(0, extURL.lastIndexOf("/"));
        else {  // from getResource
            String suffix = "/" + (aclass.getName()).replace(".", "/") + ".class";
            extURL = extURL.replace(suffix, "");
            if (extURL.startsWith("jar:") && extURL.endsWith(".jar!"))
                extURL = extURL.substring(4, extURL.lastIndexOf("/"));
        }

        return extURL;

    }
}
