/* Author: Md Farhadur Reza
 * CLID: mxr7945
 * Class: CSCE 561
 * Assignment: Assignment 2
 * Program Description: Prepare Term Document Incident Matrix and Inverted Index from Documents
 * Certificate of Authenticity: I certify that I did this assignment.
*/

import java.text.DecimalFormat;
import java.util.*;
import java.io.*;




public class Term_Document_Matrix {
	
	
	
    
    public static void main(String[] args)
    {
    	
    	
    	//List of StopWords
    	String []stopWords = {"a","an","and","are","as","at","be","by","for","from","has","he","in"
				 ,"is","it","its","of","on","that","the","to","was","were","will","with"};
    	
    	String []stopwords_doc = {".i","doc_01.txt", "doc_02.txt"};
    	
    	//String List of Vowels for Stemming
    	String [] vowels={"a","e","i","o","u"};
    	// Map List to store Keys and Documents List to store the presence of Key in the respective
    	//Documents.
    	Map<String, List<Tuple>> index = new HashMap<String, List<Tuple>>();    			 	
		List <String> list = Arrays.asList(stopWords); // make StoWords a list
		List <String> list2 = Arrays.asList(vowels);    //make vowels a list
		List <String> list5 = Arrays.asList(stopwords_doc); 
		//Directory to search for the documents 
        String directoryName="C:\\java_workspace\\561_Assignment1_mxr7945\\src\\stemmed_doc\\";        
        //create a file instance from the DirectoryName 
        File directory = new File(directoryName);
        //Array of File List for all the files in this directory
        File[] fList = directory.listFiles();
        String dir[]=directory.list();
        //List of file names
        List<String> files = new ArrayList<String>();
        // List to store Keys after tokenize the documents
        List<String> tokens = new ArrayList<String>();
        List<String> query_list = new ArrayList<String>();
        List<String> query_list2 = new ArrayList<String>();
        //List of tokens to indicate whether it is already encountered
        List<String> tokens_dup = new ArrayList<String>();
        
        List<String> doc_tokens = new ArrayList<String>();
        int docSize=0;
        
        double freqMtrx[][],queryRel[][];
        double normMtrx[][], queryArr[],queryArr2[];
        
        String strLine=null;
        
    
        BufferedWriter out;
        FileWriter fp;
        File file6;
	     
	    try{
        
	    	out = new BufferedWriter(new FileWriter("C:\\java_workspace\\561_Assignment1_mxr7945\\src\\out.txt", true));
	    	
	    	PrintWriter fop = new PrintWriter(new FileWriter("C:\\java_workspace\\561_Assignment1_mxr7945\\src\\out2.txt",true)); 
	    	 
	    	 file6 = new File("C:\\java_workspace\\561_Assignment1_mxr7945\\src\\out2.txt");
				fp = new FileWriter(file6);
	    	  //out.write("count is:");
	    	//out.flush();
       // File name=new File(directory_name);
        if (directory.isDirectory())
        {
           //String rdLine;            
            
            for( int i=0; i<dir.length; i++ )
            {
                File temp=new File(directory,dir[i]);
                
                try
                {
                    RandomAccessFile raf = new RandomAccessFile(temp,"r");
                    
                    while ( (strLine = raf.readLine()) != null )
                    {                
                        StringTokenizer st = new StringTokenizer(strLine);
                        strLine = st.nextToken();
                        
                        if( ".I".equals(strLine) )
                        {   
                            docSize++;
                        }
                        else
                        {
                            if( !tokens.contains(strLine) )
                                tokens.add(strLine);
                        }
                    }
                    raf.close();
                    
                }catch(IOException e2)
                {
                    System.out.println(e2);
                }                        
            }    
        
        }
        
                
        System.out.println("Term Document Incidence Matrix:");  
        fop.write("Term Document Incidence Matrix:");
        fop.println();
       //fop.write(fop.format("%s")+" \n");
        //sort the tokens/terms into alphabatically ordered
        Collections.sort(tokens);   
        //print the document IDs and align the matrix
        System.out.println("\t\t\t");
        String filename2, filename3,filename4;
        for(File file : fList){
        	filename2= file.getName();
        	filename4= filename2.substring(filename2.length()-10,filename2.length()-4);
        	//System.out.format("%20s",filename3);
        	
        	filename3= filename2.substring(filename2.length()-5,filename2.length()-4);
        	if(filename3.equals("1")){
        		System.out.format("%27s",filename4);
        		fop.write(filename4+" ");
        	}
        	else{
        	fop.write(filename4+" ");
        	}
        	
        	
        	
        }
        
        System.out.println();
        //fop.write("\n");
        fop.println();
        //declare two dimensional array with terms size as Rows and document size as Columns
        // to store 1 or 0 to indicate presence of terms in the document
        int[][] TD = new int[tokens.size()][docSize];
        freqMtrx = new double[tokens.size()][docSize];
        normMtrx = new double[tokens.size()][docSize];
        queryArr = new double[tokens.size()];
        queryArr2 = new double[tokens.size()];
                
        
        for( int i=0; i<dir.length; i++ )
        {
            File temp=new File(directory,dir[i]);
            
            try
            {
                RandomAccessFile raf = new RandomAccessFile(temp,"r");
                
                while ( (strLine = raf.readLine()) != null )
                {                
                    StringTokenizer st = new StringTokenizer(strLine);
                    strLine = st.nextToken();
                    
                    if( ".I".equals(strLine) )
                    {
                        
                    }
                    else
                    {                                 
                        int xindx = tokens.indexOf(strLine);                                                        
                        
                        int x1 = Integer.parseInt(st.nextToken());
                        freqMtrx[xindx][i] = x1;
                        
                        double x2 = Double.parseDouble(st.nextToken());
                        normMtrx[xindx][i] = x2;                            
                        //normMtrx[xindx][i] = 0.0;                            
                    }
                }                    
                
                raf.close();
                
            }catch(IOException e2)
            {
                System.out.println(e2);
            }                        
        }
        
        
       	   
        fop.println();
    		   for( int ri = 0 ; ri<tokens.size(); ri++ )
               {
    			   if(!tokens_dup.contains(tokens.get(ri))){
    			   System.out.format("%-15s",tokens.get(ri)+" ");
    			   fop.write(tokens.get(ri)+" ");
    			   for (int k=0; k<docSize;k++)
    			   {
   			  		                
    				   
                       System.out.format("%12s",freqMtrx[ri][k]);
                       fop.write(freqMtrx[ri][k]+" ");
                   }
                   
                
                   System.out.println();
                   fop.write("\n");
                   fop.print("\n");
                   fop.println();
               }	
               }
    		   
    		  
    		   fop.println();
    		   
    		   System.out.println("Normalized Term Document Incidence Matrix:"); 
    		   fop.write("Normalized Term Document Incidence Matrix:");
    		   fop.println();
    	        //sort the tokens/terms into alphabatically ordered
    	        Collections.sort(tokens);   
    	        //print the document IDs and align the matrix
    	        System.out.println("\t\t\t");
    	        //String filename2, filename3,filename4;
    	        for(File file : fList){
    	        	filename2= file.getName();
    	        	filename4= filename2.substring(filename2.length()-10,filename2.length()-4);
    	        	//System.out.format("%20s",filename3);
    	        	
    	        	filename3= filename2.substring(filename2.length()-5,filename2.length()-4);
    	        	if(filename3.equals("1")){
    	        		System.out.format("%27s",filename4);
    	        		fop.write(filename4+" ");
    	        	}
    	        	else{
    	        	System.out.format("%12s",filename4);
    	        	fop.write(filename4+" ");
    	        	}
    	        }
    	        System.out.println();
    	       // fop.write("\n");
    	        fop.println();
    		   
    		   
    		  System.out.println("Normalized Term Document Matrix");
    		   
    		   for( int ri = 0 ; ri<tokens.size(); ri++ )
               {
    			   if(!tokens_dup.contains(tokens.get(ri))){
    			   System.out.format("%-15s",tokens.get(ri)+" ");
    			   fop.write(tokens.get(ri)+" ");
    			   for (int k=0; k<docSize;k++)
    			   {
   			  		                
                       
                       System.out.format("%12s",normMtrx[ri][k]);
                       fop.print(normMtrx[ri][k]+" ");
                   }
                   
                
                   System.out.println();
                 //  fop.write("\n");
                   fop.println();
               }	
               }
              
    		   fop.println();
    		   
    		   
    		   
    	// Display the term document incidence matrix in the console
    	/* for (int j=0; j<tokens.size();j++ ) 
		   {
    		 if(!tokens_dup.contains(tokens.get(j))){ //check whether token is already encountered or presented
    		 System.out.format("%-12s",tokens.get(j)+" ");
    		 
    		
			   for (int k=0; k<docSize;k++)
			   {
				  
				   System.out.format("%12s",normMtrx[j][k]);
				   
				  
			   }
			   tokens_dup.add(tokens.get(j)); //insert the already showed terms into array for tracking
			   System.out.println();
    		 }
		   }
    	*/
    	
        //take the user query input
    	
    		for(int i=0;i<tokens.size();i++)
    			  queryArr[i]=0.0;
    
    		
    		
                      
    	try {   
             System.out.println("\n\nEnter query (space separated normalized form): ");
             
             InputStreamReader isr = new InputStreamReader(System.in);
             BufferedReader reader = new BufferedReader(isr);
         
             String query = reader.readLine();
         
             StringTokenizer stn = new StringTokenizer(query);            
         
             int qindx = 0;
             while(stn.hasMoreTokens())
             {
            	 strLine= stn.nextToken();
            	 for(int i=0;i<tokens.size();i++)
            	 {
            	  if(strLine.equals(tokens.get(i)))
            	  {
                     //double tdq = Double.parseDouble(stn.nextToken());
            		  qindx=tokens.indexOf(strLine);
            		  //System.out.println(qindx+" ");
                     queryArr[qindx] = 1.0;
                // qindx++;
                     query_list.add(strLine);
            	    }
            	 }
            	 
            	  
             }
            /* while(qindx<tokens.size())
             {
                 queryArr[qindx] = 0.0;
                 qindx++;
             }*/
         
             System.out.println("\n");
             
         }catch (IOException e)
         {
             System.out.println(e);
         }
    	 
    	//System.out.println("Query Vector is:"+"\n");
    	 
    /*	for (int i=0;i<tokens.size();i++)
    		System.out.println(queryArr[i]+"\n");*/
    	
    	double qval = 0, dval = 0, denominator;
    	double numerator[] = new double[docSize];  
    	
    	for( int qi = 0; qi<queryArr.length; qi++)
            qval += Math.pow(queryArr[qi], 2.0);
        
        qval = Math.sqrt(qval);
    	 
        
        for( int dindx = 0; dindx<docSize; dindx++)
        {
            for( int rindx = 0; rindx<tokens.size(); rindx++)
            {
                numerator[dindx] += normMtrx[rindx][dindx]*queryArr[rindx];
            }
        }
        
        RSV[] trs = new RSV[docSize];
        
        for( int dindx = 0; dindx<docSize; dindx++)
        {
            for( int rindx = 0; rindx<tokens.size(); rindx++)
            {
                dval += Math.pow( normMtrx[rindx][dindx], 2.0);                   
            }
            
            dval = Math.sqrt(dval);
            
            denominator = qval*dval;
            
            
            RSV trc = new RSV();
            
            if ( denominator!=0 )
                trc.rsvVal = numerator[dindx]/denominator;
            else
                trc.rsvVal = 0.0;
            
            trc.doc = "doc_"+String.format("%02d",dindx+1)+".txt";
            
            trs[dindx] = new RSV(trc);                
        }   
        
        System.out.println("\n");
        System.out.println("The Document order for the given query according to RSV value is:");
        fop.write("The Document order for the given query is:");
        Arrays.sort(trs);
        for (int i = 0; i< trs.length; i++)
        {
            System.out.println(trs[i].doc+ " "+trs[i].rsvVal);
           // fop.write(trs[i].doc+ " "+trs[i].rsvVal);
        }
        //Recall and Precision for the given query
        
        String directoryName4="C:\\java_workspace\\561_Assignment1_mxr7945\\src\\qrels\\";
        File directory4 = new File(directoryName4);
        //Array of File List for all the files in this directory
        File[] fList4 = directory4.listFiles();
        String dir4[]=directory4.list();
        
        queryRel = new double[1][50];
        
        int querySize=0;
       // int xindx = 0,x1;  
        int queryIndex=0;
        
       for (int i=1;i<= docSize;i++)
            doc_tokens.add("doc_"+String.format("%02d",i)+".txt");	
        
        
     /*   for (int i=0;i< docSize;i++)
            System.out.println(doc_tokens.get(i));*/
        
        int g=0,w=0;
        for( g=0; g<50; g++ )
        {
        	 //for( w=0; w<docSize; w++ )
             //{
        		 queryRel[w][g]=0.0;
             //}
        }
        
        int file_select=0;
        
      /*  for( int i=0; i<dir4.length; i++ )
        {
            File temp=new File(directory4,dir4[i]);
            
            try
            {
                RandomAccessFile raf = new RandomAccessFile(temp,"r");
                
                while ( (strLine = raf.readLine()) != null )
                {                
                    StringTokenizer st = new StringTokenizer(strLine);
                    strLine = st.nextToken();
                    
                    if(query_list.contains(strLine))
                    {
                    	file_select=i;
                    	break;
                    }
                   
                    
                }                    
                
                raf.close();
                
            }catch(IOException e2)
            {
                System.out.println(e2);
            }                        
        }
        */
        
        System.out.println("Which Query Relevance File you want to Search: ");
        Scanner in = new Scanner(System.in);
		int file_number = in.nextInt();
       
		fop.println();
        fop.write("Which Query Relevance File you want to Search "+file_number);
        
        
        double Recall[][]= new double[1][5];
        double Precision[][]= new double[1][5];
        
        
        
     //   for( int q=0; q<1; q++ )
       // {
            File temp=new File(directory4,dir4[file_number-1]);
            
            try
            {
                RandomAccessFile raf = new RandomAccessFile(temp,"r");
                
                while ( (strLine = raf.readLine()) != null )
                {                
                    StringTokenizer st = new StringTokenizer(strLine);
                    strLine = st.nextToken();
                    
                    //if(query_list.contains(strLine)){
                    
                    
                    	//strLine = st.nextToken();
                    if(strLine.startsWith("doc"))
                    {
                   
                        int xindx = doc_tokens.indexOf(strLine);                                                        
                        //System.out.println(" xindex is:"+xindx);
                        int x1 = Integer.parseInt(st.nextToken());
                        queryRel[0][xindx] = x1;
                                                
                   // }
                    }
                 
                    
                }    
                raf.close();
                
            }catch(IOException e2)
            {
                System.out.println(e2);
            }    
       // }     
                                  
        
        
        
        
      /*  System.out.println("Query Relevance Matrix");
		   
		   for( int ri = 0 ; ri<10; ri++ )
        {
            
			   for (int k=0; k<docSize;k++)
			   {
		  		                
                
                System.out.print(queryRel[ri][k]+"  ");
            }
            
         
            System.out.println();
        }	
        
        */
        
       /* System.out.println("query and document relevancy is:");
        for (int h=0;h<docSize; h++)
        	 System.out.println(queryRel[0][h]);*/
        
        double total_rel[]= new double[dir4.length];
        double total_rel2[]= new double[5];
        
        //for (int k=0; k<dir2.length;k++)
        	//total_rel[k]=0;
        
        
        for (int m=0; m<5;m++)
        	total_rel2[m]=0;
       
       int total_relevant=0;
      //  for (int i=0;i<dir2.length; i++)
        	for (int l=0;l< docSize; l++)
        	{
        		if(queryRel[0][l]>0)
        			total_relevant+=1;
        	}
        
        	int count5=0;
        	
        	for (int a=10;a<=docSize; a+=10)
        	{
        		for(int b=0;b<a;b++)
        		{
        		   if(queryRel[0][b]>0)
        			total_rel2[count5]+=1;
        		
        		}
        		count5+=1;
        	}
        	
        
      /*  for (int k=0; k<dir2.length;k++)
        	total_rel[k]=0;
       
       
        for (int i=0;i<dir2.length; i++)
        	for (int j=0;j< docSize; j++)
        	{
        		if(queryRel[i][j]>0)
        			total_rel[i]+=1;
        	}
       */
        	  //total_rel[i] += queryRel[i][j];
        
        double relevant=0;
        double non_relevant=0;
      
     
        
      //  for (int k=0;k<10;k++)
        //{
           int rel_retr=0;
           int rel_not_retr=0;
           int retr_not_rel=0;
           int f;
           String z;
           int count6=0;
           int s,o;
        	 for (s=10;s<=50;s+=10)
        	{
        		 System.out.println("retrieved/not_retrieved/relevant/non_relevant Statuses at cut points:  "+(s));
        		 for (o=0;o<s;o++)
        		 {
        			if(trs[o].rsvVal>0)
        			{
        				//z=trs[i].doc.toString();
        				//trs[i].doc.s
        				f=Integer.parseInt(trs[o].doc.substring(trs[o].doc.length()-6,trs[o].doc.length()-4));
        				//f= Integer.parseInt(substring((trs[i].doc.length())-2,trs[i].doc.length()));
        			//	System.out.println("DocID"+f);
        				if(queryRel[0][f-1]>0)
        				{
        					rel_retr+=1;
        				}
        				else
        					retr_not_rel+=1;
        			}
        			else if(trs[o].rsvVal<0)
        			{
        				//z=trs[i].doc.toString();
        				//trs[i].doc.s
        				f=Integer.parseInt(trs[o].doc.substring(trs[o].doc.length()-6,trs[o].doc.length()-4));
        				//f= Integer.parseInt(substring((trs[i].doc.length())-2,trs[i].doc.length()));
        			//	System.out.println("DocID"+f);
        				if(queryRel[0][f-1]>0)
        				{
        					rel_not_retr+=1;
        				}
        			}
        		 }
        		 if(total_relevant>0)
        		   Recall[0][count6]=(double) (rel_retr) /( total_relevant);
        		 
        		 Precision[0][count6]=(double) (rel_retr) /( s);
        		/* if((rel_retr+rel_not_retr)>0)
        		 {
        		 Precision2[count6]=(double) (rel_retr) /( rel_retr+rel_not_retr);
        		 }
        		 else 
        			 Precision2[count6]=0;*/
        		 System.out.println("relevant_retrieved: "+rel_retr+" relevant_not_retrieved: "+rel_not_retr+ " retrieved_not_relevant: "+retr_not_rel+" total actual relevant: "+total_relevant);
        		 fop.println();
        		 fop.write("relevant: "+rel_retr+" "+rel_not_retr+ " "+retr_not_rel+" "+total_relevant);
        		 fop.println();
        		 rel_retr=0;
        		 rel_not_retr=0;
        		 retr_not_rel=0;
        		 count6++;
        		 System.out.println();
        	 }
        //}
        
        	 
        	
       /* for(j=0;j<5;j++)
        		System.out.println("Recall: "+ Recall[i][j]+ "  "+"Precision"+Precision[i][j]);*/
        	
        //System.out.println(dir2.length);
        
        //out.flush();
        	
        	/*raf.close();
            
            }catch(IOException e2)
            {
                System.out.println(e2);
            }      
        	*/
            //}
            
        	 System.out.println();
            for(int j=0;j<5;j++)
        	{
            	System.out.println("Recall and Precision at cut points: "+(j*10+10));
            	//System.out.println();
    		 System.out.println("Recall: "+ Recall[0][j]+ "  "+"Precision: "+Precision[0][j]);
    		 fop.write("Recall: "+ Recall[0][j]+ "  "+"Precision: "+Precision[0][j]);
    		 fop.println();
    		 System.out.println();
        	}
            
	    //Got the RSV and Recall-Precision value for the given query
        
        
        
        //Now we have to calculate recall and precision for every query and average that
        
        //RSV Values for all queries
        
            
            for (int i=0;i<tokens.size();i++)
        		queryArr2[i]=0;    
            
        String directoryName3="C:\\java_workspace\\561_Assignment1_mxr7945\\src\\queries\\";
        File directory3 = new File(directoryName3);
        //Array of File List for all the files in this directory
        File[] fList3 = directory3.listFiles();
        String dir3[]=directory3.list();
        
        
     /*   File temp3=new File(directory3,dir3[0]);
        
        try
        {
            RandomAccessFile raf = new RandomAccessFile(temp3,"r");
            
            while ( (strLine = raf.readLine()) != null )
            {                
                StringTokenizer st = new StringTokenizer(strLine);
                strLine = st.nextToken();
                
                while(!strLine.matches("\n")){
                	query_list2.add(strLine);
                	strLine = st.nextToken();
                	
                }
                
        
            }                    
        raf.close();
        } catch (IOException e)
  	  {//Catch exception if any
  	  System.err.println("Error: " + e.getMessage());
  	  }
      */  
        
        
        
        
        int i=0,j=0;
        int d=0;
        
        double Recall2[][]= new double[10][5];
        double Precision2[][]= new double[10][5];
       
        
        int rel_retr2=0;
        int rel_not_retr2=0;
        int retr_not_rel2=0;
        int total_relevant2=0;
        
        double queryRel2[] = new double[docSize];
        double qval2 = 0, dval2 = 0, denominator2;
       	double numerator2[] = new double[docSize]; 
       	RSV trs2[] = new RSV[docSize];
       	RSV trc2 = new RSV();
       	
        System.out.println("How many queris do you want to average for Recall and Precision: ");
        Scanner in2 = new Scanner(System.in);
		int no_of_query = in2.nextInt();
       	
   for(d=0;d<no_of_query;d++)  //loop to get the user input for 10 times
     {
	   
	   
	     /*rel_retr2=0;
		 rel_not_retr2=0;
		 retr_not_rel2=0;
		 total_relevant2=0;
		 qval2=0;
       dval2=0;
       denominator2=0;
       for(j=0;j<docSize;j++)
       {
      	 numerator2[j]=0;
      	 queryRel2[j]=0;
      	 trs2[j].rsvVal=0;
      	 trc2.rsvVal=0;
       }*/
       
	   
        try {   
            System.out.println("\n\nEnter query (space separated normalized form): ");
            
            InputStreamReader isr = new InputStreamReader(System.in);
            BufferedReader reader = new BufferedReader(isr);
        
            String query = reader.readLine();
        
            StringTokenizer stn = new StringTokenizer(query);            
        
            int qindx = 0;
            while(stn.hasMoreTokens())
            {
           	 strLine= stn.nextToken();
           	 for(i=0;i<tokens.size();i++)
           	 {
           		 if(strLine.equals(tokens.get(i)))
           		 {
                    //double tdq = Double.parseDouble(stn.nextToken());
           		  qindx=tokens.indexOf(strLine);
           		  //System.out.println(qindx+" ");
                    queryArr2[qindx] = 1.0;
               // qindx++;
                    query_list.add(strLine);
           		 }
           	 }
           	 
           	  
            }
           /* while(qindx<tokens.size())
            {
                queryArr[qindx] = 0.0;
                qindx++;
            }*/
        
            System.out.println("\n"); 
                
               
        
   	 
   	//System.out.println("Query Vector is:"+"\n");
   	 
  /* 	for (i=0;i<tokens.size();i++)
   		System.out.println(queryArr[i]+"\n");*/
   	
   	
   	
   	//qval2 = 0, dval2 = 0, denominator2;
   	//double numerator2[] = new double[docSize]; 
   	
   	int querySize2=10;
   	
   	for( int qi = 0; qi<queryArr2.length; qi++)
           qval2 += Math.pow(queryArr2[qi], 2.0);
       
       qval2 = Math.sqrt(qval2);
   	 
       
       for( int dindx = 0; dindx<docSize; dindx++)
       {
           for( int rindx = 0; rindx<tokens.size(); rindx++)
           {
               numerator2[dindx] += normMtrx[rindx][dindx]*queryArr2[rindx];
           }
       }
       
       //RSV[] trs2 = new RSV[querySize2][docSize];
      
       
       for( int dindx = 0; dindx<docSize; dindx++)
       {
           for( int rindx = 0; rindx<tokens.size(); rindx++)
           {
               dval2 += Math.pow( normMtrx[rindx][dindx], 2.0);                   
           }
           
           dval2 = Math.sqrt(dval2);
           
           denominator2 = qval2*dval2;
           
           
          
           
           if ( denominator2!=0 )
               trc2.rsvVal = numerator2[dindx]/denominator2;
           else
               trc2.rsvVal = 0.0;
           
           trc2.doc = "doc_"+String.format("%02d",dindx+1)+".txt";
           
           trs2[dindx] = new RSV(trc2);                
       }   
        
       //raf.close();
       
                               
        
        
        
        
        
        System.out.println("\n");
        System.out.println("The Document order for the"+d+"th"+" query according to their RSV:");
        Arrays.sort(trs2);
        for (i = 0; i< trs2.length; i++)
        {
            System.out.println(trs2[i].doc+ " "+trs2[i].rsvVal);
           // fop.write(trs2[i].doc+ " "+trs2[i].rsvVal);
        }
        
        String directoryName2="C:\\java_workspace\\561_Assignment1_mxr7945\\src\\qrels\\";
        File directory2 = new File(directoryName2);
        //Array of File List for all the files in this directory
        File[] fList2 = directory2.listFiles();
        String dir2[]=directory2.list();
        
        
        
       // int querySize=0;
       // int xindx = 0,x1;  
        //int queryIndex=0;
        
     /*  for (int i=1;i<= docSize;i++)
            doc_tokens.add("doc_"+String.format("%02d",i)+".txt");	*/
        
        
     /*   for (int i=0;i< docSize;i++)
            System.out.println(doc_tokens.get(i));*/
        
       // for( i=0; i<10; i++ )
        //{
        	 
      //  }
        
        //int file_select=0;
        
     /*   for( int i=0; i<dir2.length; i++ )
        {
            File temp=new File(directory2,dir2[i]);
            
            try
            {
                RandomAccessFile raf = new RandomAccessFile(temp,"r");
                
                while ( (strLine = raf.readLine()) != null )
                {                
                    StringTokenizer st = new StringTokenizer(strLine);
                    strLine = st.nextToken();
                    
                    if(query_list.contains(strLine))
                    {
                    	file_select=i;
                    	break;
                    }
                   
                    
                }                    
                
                raf.close();
                
            }catch(IOException e2)
            {
                System.out.println(e2);
            }                        
        }
        */
       // System.out.println("search the file"+file_select);
        
        
        //double Recall[][]= new double[dir2.length][5];
        //double Precision[][]= new double[dir2.length][5];
       // for( i=0; i<dir2.length; i++ )
        //{
            File temp5=new File(directory2,dir2[d]);
            
            System.out.println("Selected file"+dir2[d]);
            
            try
            {
                RandomAccessFile raf = new RandomAccessFile(temp5,"r");
                
                while ( (strLine = raf.readLine()) != null )
                {                
                    StringTokenizer st = new StringTokenizer(strLine);
                    strLine = st.nextToken();
                    
                 //   if(query_list.contains(strLine)){
                    	
                 /*  for(String s: query_list)
                    {
                    	if(strLine.equalsIgnoreCase(s))
                    	{
                        
                    	}
                    }*/
                    	//strLine = st.nextToken();
                    		if(strLine.startsWith("doc"))
                    		{
                   
                    			int xindx = doc_tokens.indexOf(strLine);                                                        
                        
                    			int x1 = Integer.parseInt(st.nextToken());
                    			queryRel2[xindx] = x1;
                                                
                            }
                        // }
                   // }
                  /*  else{
                    	query_list.add(strLine);
                    }*/
                    
                }                    
                
                                  
        
        
        
        
      /*  System.out.println("Query Relevance Matrix");
		   
		   for( int ri = 0 ; ri<10; ri++ )
        {
            
			   for (int k=0; k<docSize;k++)
			   {
		  		                
                
                System.out.print(queryRel[ri][k]+"  ");
            }
            
         
            System.out.println();
        }	
        
        */
        
        System.out.println("query and document actual relevancy is:");
        for (int h=0;h<docSize; h++)
        {
        	 System.out.println(queryRel2[h]+" ");
        	 //fop.write((int)queryRel2[h]);
        }
        
        double total_rel3[]= new double[dir2.length];
        double total_rel4[]= new double[5];
        
        //for (int k=0; k<dir2.length;k++)
        	//total_rel[k]=0;
        
        
        for (int m=0; m<5;m++)
        	total_rel2[m]=0;
       
      // int total_relevant2=0;
      //  for (int i=0;i<dir2.length; i++)
        	for (int l=0;l< docSize; l++)
        	{
        		if(queryRel2[l]>0)
        			total_relevant2+=1;
        	}
        
        	int count7=0;
        	
        	for (int a=10;a<=docSize; a+=10)
        	{
        		for(int b=0;b<a;b++)
        		{
        		   if(queryRel2[b]>0)
        			total_rel4[count7]+=1;
        		
        		}
        		count7+=1;
        	}
        	
     
        
        double relevant2=0;
        double non_relevant2=0;
      
     
        
        
        
      //  for (int k=0;k<10;k++)
        //{
          
           int f2;
           String z2;
           int count8=0;
        	 for (j=10;j<=50;j+=10)
        	{
        		 System.out.println("retrieved/not_retrieved/relevant/non_relevant Statuses at cut points:  "+(j));
        		 for (i=0;i<j;i++)
        		 {
        			if(trs2[i].rsvVal>0)
        			{
        				//z=trs[i].doc.toString();
        				//trs[i].doc.s
        				f2=Integer.parseInt(trs2[i].doc.substring(trs2[i].doc.length()-6,trs2[i].doc.length()-4));
        				//f= Integer.parseInt(substring((trs[i].doc.length())-2,trs[i].doc.length()));
        				System.out.println("DocID"+f2);
        				fop.write("DocID"+f2);
        				if(queryRel2[f2-1]>0)
        				{
        					rel_retr2+=1;
        				}
        				else
        					retr_not_rel2+=1;
        			}
        			else if(trs2[i].rsvVal<0)
        			{
        				//z=trs[i].doc.toString();
        				//trs[i].doc.s
        				f2=Integer.parseInt(trs2[i].doc.substring(trs2[i].doc.length()-6,trs2[i].doc.length()-4));
        				//f= Integer.parseInt(substring((trs[i].doc.length())-2,trs[i].doc.length()));
        				System.out.println("DocID"+f2);
        				fop.write("DocID"+f2);
        				if(queryRel2[f2-1]>0)
        				{
        					rel_not_retr2+=1;
        				}
        			}
        		 }
        		 if(total_relevant2>0)
        		   Recall2[d][count8]=(double) (rel_retr2) /( total_relevant2);
        		 
        		 Precision2[d][count8]=(double) (rel_retr2) /(j);
        		/* if((rel_retr+rel_not_retr)>0)
        		 {
        		 Precision2[count6]=(double) (rel_retr) /( rel_retr+rel_not_retr);
        		 }
        		 else 
        			 Precision2[count6]=0;*/
        		 System.out.println("relevant_retrieved: "+rel_retr2+" relevant_not_retrieved: "+rel_not_retr2+ " retrieved_not_relevant: "+retr_not_rel2+" total actual relevant: "+total_relevant2);
        		// System.out.println("relevant: "+rel_retr2+" "+rel_not_retr2+ " "+retr_not_rel2+" "+total_relevant2);
        		 fop.println();
        		 fop.write("relevant: "+rel_retr2+" "+rel_not_retr2+ " "+retr_not_rel2+" "+total_relevant2);
        		 fop.println();
        		 rel_retr2=0;
        		 rel_not_retr2=0;
        		 retr_not_rel2=0;
        		 //total_relevant2=0;
        		 count8++;
        		 System.out.println();
        	 }
        //}
        	 
        	 
        	
       /* for(j=0;j<5;j++)
        		System.out.println("Recall: "+ Recall[i][j]+ "  "+"Precision"+Precision[i][j]);*/
        	
        //System.out.println(dir2.length);
        
        //out.flush();
        	
        	raf.close();
            
            }catch(IOException e2)
            {
                System.out.println(e2);
            }      
        	
           // }
     //   }
        
        }catch(IOException e2)
        {
            System.out.println(e2);
        }   
        
      /*   rel_retr2=0;
		 rel_not_retr2=0;
		 retr_not_rel2=0;*/
		 total_relevant2=0;
		 qval2=0;
         dval2=0;
         denominator2=0;
         for(j=0;j<docSize;j++)
         {
        	 numerator2[j]=0;
        	 queryRel2[j]=0;
        	 trs2[j].rsvVal=0;
        	 trc2.rsvVal=0;
         }
         for(j=0;j<queryArr2.length;j++)
         {
        	 
        	 queryArr2[j]=0;
         }
         
	    }
        //for loop closure for RSV and Recall for all queries
        
        
        //Average Recall and Precision Calculation
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        double Recall_sum[]=new double[5];
        double Precision_sum[]=new double[5] ;
        
        double Recall_avg[]=new double[5];
        double Precision_avg[]=new double[5] ;
        
        for(j=0;j<5;j++)
        {
        	Recall_sum[j]=0.0;
        	Precision_sum[j]=0.0;
        }
        
        System.out.println();
        for(i=0;i<no_of_query;i++)
        {
        	System.out.println("Recall and Precision for Query-"+(i+1)+"\n");
        	fop.write("Recall and Precision for Query-"+(i+1));
        	for(j=0;j<5;j++)
        	{
        		System.out.println("Recall and Precision at cut points: "+(j*10+10)+"\n");
    		System.out.println("Recall: "+ Recall2[i][j]+ "  "+"Precision: "+Precision2[i][j]+"\n");
    		
    		fop.write("Recall and Precision at cut points: "+(j*10+10));
    		fop.write("Recall: "+ Recall2[i][j]+ "  "+"Precision: "+Precision2[i][j]);
    		fop.println();
        	}
        
        }
        
        for(j=0;j<5;j++)
        {
        for(i=0;i<no_of_query;i++)
        {
        
    		//System.out.println("Recall: "+ Recall[i][j]+ "  "+"Precision"+Precision[i][j]);
        	Recall_sum[j] += Recall2[i][j];
        	Precision_sum[j] += Precision2[i][j];
        }
        
        }
        
        for(j=0;j<5;j++)
        {
        	Recall_avg[j]= Recall_sum[j]/no_of_query;
        	Precision_avg[j]= Precision_sum[j]/no_of_query;
        }
        
        System.out.println();
        System.out.println("Average Recall and Precision for all the quries\n");
        
        for(j=0;j<5;j++)
        {
        	System.out.println(" Recall and Precision at Cut"+(j*10+10)+" Points");
        	System.out.println("Recall:  "+Recall_avg[j]+" Precision: "+ Precision_avg[j]);
        	fop.println();
        	fop.write(" Recall and Precision at Cut"+(j*10+10)+" Points");
        	fop.write( "Recall:  "+Recall_avg[j]+" Precision: "+ Precision_avg[j]);
        	fop.println();
        	System.out.println();
        }
        fop.close();
        
       // out.close();
 	   // dos.close();
 	  // try{
 	    //	BufferedWriter out= new BufferedWriter(new FileWriter("C://out.txt")); 
 	    	//out.write("Total Distance for all Sources and Destinations:"+ dijk_distance);
    	  
            
            
            
	    }
 	    catch (IOException e)
    	  {//Catch exception if any
    	  System.err.println("Error: " + e.getMessage());
    	  }
 	   // finally {
 			//out.close();
 	//}
 	
	    
        
     }
        
    //Class Tuple is used to store the documents index in ordered list.
    private static class Tuple {
		private int fileno;
		//private double weight;
 
		public Tuple(int fileno) {
			this.fileno = fileno;
			//this.weight=weight;
		}
	}
   /* private static class Tuple {
		private int fileno;
		private double weight;
 
		public Tuple(int fileno, double weight) {
			this.fileno = fileno;
			this.weight=weight;
		}
	}*/
    
    
    public static boolean find(File f, String searchString) {
        boolean result = false;
        Scanner in = null;
        try {
            in = new Scanner(new FileReader(f));
            while(in.hasNextLine() && !result) {
                result = in.nextLine().indexOf(searchString) >= 0;
            }
        }
        catch(IOException e) {
            e.printStackTrace();      
        }
        finally {
            try { in.close() ; } catch(Exception e) { /* ignore */ }  
        }
        return result;
    }
    
    //check the presence of terms in the document
    public static boolean fileContainsString(File file, String aString) throws FileNotFoundException{
    	  
        FileInputStream fis = null;
        BufferedReader in = null;
    
        try{
        	//open the file
           fis = new FileInputStream(file);
           //open a reader to read the file
           in = new BufferedReader(new InputStreamReader(fis));
     
           String currentLine;
           while ((currentLine = in.readLine()) != null) //check till the end of file is reached
           {
        	  //if the term is found in the document, return true 
              if(currentLine.toLowerCase().indexOf(aString) >= 0)  return true;
           }
     
         //Catch exception if any
        }catch(IOException ioe){
           ioe.printStackTrace();
        }finally{
           try{
              if(in != null) in.close();
              if(fis != null) fis.close();
           }catch(IOException ioe){ }
        }
        return false;
     } 
    
   
    
    public static double tfCalculator(List<String> totalterms, String termToCheck) {
        double count = 0;  //to count the overall occurrence of the term termToCheck
        for (String s : totalterms) {
            if (s.equalsIgnoreCase(termToCheck)) {
                count++;
            }
        }
        //return count / totalterms.length;
        return count;
    }
    
    public static double roundThreeDecimals(double d) {
        DecimalFormat threeDForm = new DecimalFormat("#.###");
    return Double.valueOf(threeDForm.format(d));
}
    
    
}
