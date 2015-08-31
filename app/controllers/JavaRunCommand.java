

import java.io.*;



    // run this way

    // javac JavaRunCommand.java

    // java -classpath . JavaRunCommand



    public class JavaRunCommand {



        public static void main(String args[]) {



        String st = null;



        try {



            //String[] callAndArgs= {"python","my_python.py","arg1","arg2"};
            String[] callAndArgs= {"python","my_python.py"};

            Process p = Runtime.getRuntime().exec(callAndArgs);

            

            BufferedReader stdInput = new BufferedReader(new 

                 InputStreamReader(p.getInputStream()));



            BufferedReader stdError = new BufferedReader(new 

                 InputStreamReader(p.getErrorStream()));



            // read the output

            while ((st = stdInput.readLine()) != null) {

                System.out.println(st);

            }

            

            // read any errors

            while ((st = stdError.readLine()) != null) {

                System.out.println(st);

            }

            

            System.exit(0);

        }

        catch (IOException e) {

            System.out.println("exception occured");

            e.printStackTrace();

            System.exit(-1);

        }

        }

    }