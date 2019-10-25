//Name: 
//Section: 
//ID: 

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class TFIDFSearcher extends Searcher
{	
	public TFIDFSearcher(String docFilename) {
		super(docFilename);
		/************* YOUR CODE HERE ******************/
		
		/***********************************************/
	}
	
	@Override
	public List<SearchResult> search(String queryString, int k) {
		/************* YOUR CODE HERE ******************/
		Queue<SearchResult> result = new PriorityQueue<>(k, Collections.reverseOrder());
		List<String> qTokens = tokenize(queryString);
		List<String> allTerms = new ArrayList<String>();
		for (Document doc : this.documents) {
			allTerms.addAll(doc.getTokens());
		}
		List<Double> qWeight = new ArrayList<Double>();
		for(String term : qTokens) {
			double tf = findTf(term, allTerms);
			
			int df = findDf(term);
			double idf = Math.log10(1+(this.documents.size()/df));
			
			qWeight.add(tf*idf);
		}
		double vq = 0.0;
		for(Double num : qWeight) {
			vq += Math.pow(num, 2);
		}
		vq = Math.sqrt(vq);
		
		//System.out.println(allTerms.size());
		for (Document doc : this.documents) {
			List<Double> allWeight = new ArrayList<Double>();
			for(String term : doc.getTokens()) {
				double tf = findTf(term, allTerms);
				
				int df = findDf(term);
				double idf = Math.log10(1+(this.documents.size()/df));
				
				allWeight.add(tf*idf);
	
			}
			
			double sum = 0.0;
			for(Double num : allWeight) {
				sum += Math.pow(num, 2);
			}
			sum = Math.sqrt(sum);
			
			System.out.println(allWeight.size()+" VS "+qWeight.size());
		}
		return null;
		/***********************************************/
	}
	
	public int findDf(String term) {
		int count = 0;
		for(Document doc : this.documents) {
			if(doc.getTokens().contains(term)) {
				count++;
			}
		}
		
		return count;
	}
	
	public double findTf(String term, List<String> x) {
		int freq = Collections.frequency(x, term);
		if(freq != 0) {
			return 1+Math.log10(freq);
		} else {
			return 0;
		}
	}
}
