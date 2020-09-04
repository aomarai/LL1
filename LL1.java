public class LL1 {

    /*
    Copyright (C) Ashkan Omaraie-Hamedani
    ULID: aomarai
    aomarai@ilstu.edu
     */

    private static char[] tokens;
    private static int currentToken;

    //E -> TE'
    private static int E() {
        int n = T();
        return EPrime(n);
    }

    //E' -> +TE' | -TE' | lambda
    private static int EPrime(int n) {

        char temp = tokens[currentToken];
        int tempInt = 0;

        switch(temp){
            case '+':
                currentToken++;
                tempInt = T();
                return EPrime(n + tempInt);
            case '-':
                currentToken++;
                tempInt = T();
                return EPrime(n - tempInt);
            case '$':
            case ')':
                return n;
            default:
                System.out.println("Error during parsing of E'");
                System.exit(-1);
                return n;
        }
    }

    //T -> FT'
    private static int T(){
        int n = F();
        return TPrime(n);
    }

    //T' -> *FT'| /FT' | lambda
    private static int TPrime(int n) {
        char temp = tokens[currentToken];
        int tempInt = 0;

        switch(temp){
            case '*':
                currentToken++;
                tempInt = F();
                return TPrime(n * tempInt);
            case '/':
                currentToken++;
                tempInt = F();
                return TPrime(n / tempInt);
            case '+':
            case '-':
            case ')':
            case '$':
                break;
            default:
                System.out.println("Error during parsing of T'");
                System.exit(-1);
        }
        return n;
    }

    private static int F() {
        char temp = tokens[currentToken];
        int n, tempInt;

        if(temp == '(') {
            currentToken++;
            n = E();
            if(tokens[currentToken] == ')') {
                currentToken++;
                return n;

                //Handler for multiple parentheses
            }else if(tokens[currentToken] == '('){
                currentToken++;

                //Recursion continues here
                return n + F();
            }else{
                System.out.println("Error during parsing of F. Are all parentheses in the expression closed?");
                System.exit(-1);
                return n;
            }
        } else {
            //Parser doesn't stop until running into a non-digit
            if(Character.isDigit(temp)){
                boolean complete = false;
                String fullNumber = "";
                int i = currentToken;
                while(!complete && tokens.length > i){
                    if(Character.isDigit(tokens[i])){
                        fullNumber += tokens[i];
                        currentToken++;
                    }else{
                        complete = true;
                    }
                    i++;
                }
                return Integer.parseInt(fullNumber);
            } else{		// occurs if the symbol is neither ( nor an int
                System.out.println("Error during second half of F parsing");
                System.exit(-1);
            }
        }
        return -1;
    }


    public static void main(String args[]){
        ///Set index of current token to 0
        currentToken = 0;

        //If no arguments are input, stop running
        if (args.length==0){
            System.out.println("No input found");
            System.exit(-1);
        }
        //Removes quotes from user expression and adds $ to end, and then converts the expression into a char array
        else {
            String tempExpression = args[0];
            tempExpression = tempExpression.replaceAll("\"", "");
            tempExpression += "$";
            tokens = tempExpression.toCharArray();
        }

        //Starts the recursive function
        int output = E();

        //Checks to see if $ is reached
        if (tokens[currentToken] == '$'){
            System.out.println("Valid. Value = " + output);
        }
        else
            System.out.println("Invalid Input. Current Token Array: " + tokens);
    }
}
