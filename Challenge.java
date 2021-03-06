/*
 * ****************************************
 * QUESTION 1
 * Sort the following list in numeric order
 * 1,10,5,63,29,71,10,12,44,29,10,-1
 * ***************************************
 *
 *
 * ***************************************
 * QUESTION 2
 * 2. Find the 3rd, 58th and 10,001th prime number.
 * ***************************************
 *
 * 
 * ***************************************
 *   QUESTION 3
 *    Get Full Json path of of any item for:
 *    ex. item2 -> \itemList\items\id[1]
 * ***************************************
 *     {"itemList": {"items": [
 *     {"id": "item1"},
 *     {"id": "item2","label": "Item 2"},
 *     {"id": "item3"},
 *     {"id": "item4"},
 *     {"id": "item5"},
 *     {"id": "subItem1",
 *     "subItems": [
 *     {"id": "subItem1Item1","label": "SubItem 1"},
 *     {"id": "subItem1Item2","label": "SubItem 2"},
 *     {"id": "subItem1Item3","label": "SubItem 3"}
 *     ]
 *     },
 *     {"id": "item6"},
 *     {"id": "item7","label": "Item 7"},
 *     {"id": "subItem2",
 *     "subItems": {"id": "item1","label": "SubItem 2 item1"}
 *     }
 *     ]}}
 */
import java.util.*; 
import java.io.File;
import java.io.IOException;
import java.lang.Math;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Challenge{

    public static void main(String[] args){
    	//sorting algorithms
        int[] toSort = {1,10,5,63,29,71,10,12,44,29,10,-1};
        Random gen = new Random(); 
        int[] randomArray = new int[gen.nextInt(21)];
        //populate randomArray
        for (int i =0; i < randomArray.length; i++){
            randomArray[i] = gen.nextInt(100 + 1 + 100) - 100; //random int between (-100,100)
        }
        System.out.println("\nArray to be sorted: "+ Arrays.toString(toSort)); 
        System.out.println("Bubble Sorted array: "+ Arrays.toString(bubbleSort(toSort))); 
        System.out.println("\nArray to be sorted(random): "+ Arrays.toString(randomArray)); 
        System.out.println("Bubble Sorted array: "+ Arrays.toString(bubbleSort(randomArray))); 
        System.out.println("\nArray to be sorted: "+ Arrays.toString(toSort)); 
        System.out.println("Merge Sorted array: "+ Arrays.toString(mergeSort(toSort))); 
        System.out.println("\nArray to be sorted:(random) "+ Arrays.toString(randomArray)); 
        System.out.println("Merge Sorted array: "+ Arrays.toString(mergeSort(randomArray))); 

        System.out.println();
        
        //Finding nth prime number
        int[] primesToFind = {3, 58, 10001, 1, 2, gen.nextInt(1000)};
        for(int i = 0; i < primesToFind.length; i++){
            int nthPrime = primesToFind[i];
            System.out.println("The " + nthPrime + getOrd(nthPrime)+ " prime is " + findNthPrime(primesToFind[i]));
        }

        //Finding JSON element path
        try{
            ObjectMapper m = new ObjectMapper();
            JsonNode rootObj = m.readTree(new File("/home/anguyen/CHALLENGE/input.json")); //linux 
            //JsonNode rootObj = m.readTree(new File("C:\\Users\\alain\\Java\\Challenge\\src\\input.json")); //windows
            String[] valuesToSearch = {"Item 2","subItem1Item2","subItem2","SubItem 1", "item6", "item4","NON-EXISTENT"};
            for (int i = 0; i < valuesToSearch.length; i++){
                System.out.println("\nPath of '"+ valuesToSearch[i]+ "':\n    " + findValuePath(valuesToSearch[i],rootObj));
            }
        }
        catch (JsonMappingException e) {
            e.printStackTrace();
        }
        catch (JsonParseException e) {
            e.printStackTrace();
        }
        catch (JsonGenerationException e) {
            e.printStackTrace();
        } 
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public static int[] bubbleSort(int[] arrayIn){
        
        int temp;
        int [] rtrArray = Arrays.copyOf(arrayIn, arrayIn.length);
        boolean swapped = true;
        int passes = 0; //will only pass n-1 times
        while(swapped && passes != rtrArray.length-1){
            swapped = false;
            for(int i=0; i < rtrArray.length-1; i++){
                if (rtrArray[i] > rtrArray[i+1]){
                    temp = rtrArray[i];
                    rtrArray[i] = rtrArray[i+1];
                    rtrArray[i+1] = temp;
                    swapped = true;
                }
            }
            passes++;
        }
        return rtrArray;
    }

    public static int[] mergeSort(int[] arrayIn){
    	
    	if (arrayIn.length <= 1){
    		return arrayIn;
    	}
    	int[] left = new int[arrayIn.length/2];
    	int[] right = new int[arrayIn.length - left.length];
    	int i=0;
    	int j=0;
    	int k=0;
    	
    	left = Arrays.copyOfRange(arrayIn, 0, left.length);
    	right = Arrays.copyOfRange(arrayIn, left.length, arrayIn.length);
    	
    	mergeSort(left);
    	mergeSort(right);
    	
    	while (i < left.length && j < right.length){
    		if (left[i] < right[j]){
    			arrayIn[k] = left[i];
    			i++;
    		}
    		else{
    		    arrayIn[k] = right[j];
    		    j++;
    		}
    		k++;
    	}
    	
    	while( i < left.length){
    		arrayIn[k] = left[i];
    		i++;
    		k++;
    	}
    	while (j < right.length){
    		arrayIn[k] = right[j];
    		j++;
    		k++;
    	}
    	return arrayIn;
    }

    public static String getOrd(int n){

        int i = n % 10;
        int j = n % 100;
        
        if (i == 1 && j != 11){
            return "st";
        }
        if (i == 2 && j != 12){
            return "nd";
        }
        if (i == 3 && j != 13){
            return "rd";
        }
        return "th";
    }

    public static int findNthPrime(int n){

        if (n < 0){
            System.out.println("Nth prime must be a positive integer");
        }
        if (n <= 2){
            return n+1;
        }

        int num = 3;
        int idx = 2;
        boolean isPrime;
        while (idx != n){
            num+=2; //add go through odd numbers only
            isPrime = true;
            for(int i =3; i < (int)(Math.sqrt(num))+1; i+=2){
                if (num % i == 0){
                    isPrime = false;
                    break;
                }
            }
            if (isPrime){
                idx += 1;
            }
        }
        return num;
    }

    public static String findValuePath(String element, JsonNode jObj){  
    //this method will go depth first 
        String path = "";
        if (jObj.isArray()){
            //find element in array
            int i = 0;
            for (JsonNode temp : jObj){
                if (temp.isTextual() && temp.textValue().equals(element)){
                    return "[" + i + "]";
                }
                i++;
            }
            //element not found in array, dive deeper
            i = 0;
            for (JsonNode temp: jObj){
                String pathAdd = findValuePath(element, temp);
                if (pathAdd != ""){
                    return "[" + i + "]" + pathAdd;
                }
                i++;
            }
        }
        else if (jObj.isObject()){
            //find element in object field values 
            Iterator<Map.Entry<String,JsonNode>> fields = jObj.fields(); 
            while(fields.hasNext()){
                Map.Entry<String,JsonNode> field = fields.next();
                if(field.getValue().isTextual() && field.getValue().textValue().equals(element)){
                    return "\\" + field.getKey();
                }
            }
            //element not found in obj, dive deeper
            fields = jObj.fields();
            while(fields.hasNext()){
                Map.Entry<String,JsonNode> field = fields.next();
                String pathAdd = findValuePath(element, field.getValue());
                if (pathAdd != ""){
                	String key = field.getKey();
                    return "\\" + key + pathAdd;
                }
            }
        }
        return path;
    }
}
