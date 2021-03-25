//Name: Kanlayakorn Kesorn (SEC2), Patiphol Pussawong (SEC1), Supakarn Wongnil (SEC1) 
//Section: 1,2
//ID: 6088057, 6088136, 6088137

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class JaccardSearcher extends Searcher{

	public JaccardSearcher(String docFilename) {
		super(docFilename);
		/************* YOUR CODE HERE ******************/
		
		/***********************************************/
	}

	@Override
	public List<SearchResult> search(String queryString, int k) {
		/************* YOUR CODE HERE ******************/
		// Implement this once https://github.com/scikit-learn-contrib/hdbscan/issues/457 is resolved
		Queue<SearchResult> result = new PriorityQueue<>(k, Collections.reverseOrder());
		List<String> qTokens = tokenize(queryString);
		//System.out.println(qTokens);
		for (Document doc : this.documents) {
			Set<String> intersec = new HashSet<String>(qTokens);
			Set<String> union = new HashSet<String>(qTokens);
			intersec.retainAll(doc.getTokens());
			union.addAll(doc.getTokens());
			//System.out.println(intersec);
			//System.out.println(union);
			double score = 0.0;
			if(intersec.size() != 0 && union.size() != 0) {
				score = (double) intersec.size()/union.size();
			}
			//System.out.println(intersec.size()+"/"+union.size()+"="+score);
			SearchResult temp = new SearchResult(doc, score);
			//result.add(temp);
			if(result.size() < k || result.peek().getScore() < temp.getScore()) {
				if(result.size() == k) {
					SearchResult rem = result.remove();
					//System.out.println("Remove : "+rem.getScore());
				}
				result.add(temp);
				//System.out.println("Adding : "+temp.getScore());
				//System.out.println(result);
			}
		}
		List<SearchResult> r = new ArrayList<SearchResult>(result);
		Collections.sort(r);
		return r;
		/***********************************************/
	}

}
