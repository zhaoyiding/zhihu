package zyd.zhihu.service;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.stereotype.Service;
import zyd.zhihu.model.Question;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SearchService {
	private static final String SOLR_URL = "http://localhost:8983/solr/zhihu";
	private static final String QUESTION_TITLE_FIELD = "question_title";
	private static final String QUESTION_CONTENT_FIELD = "question_content";
	private static final String HL_PRE = "<em>";
	private static final String HL_PSOT = "</em>";
	
	private SolrClient solrClient = new HttpSolrClient.Builder(SOLR_URL).build();
	
	public List<Question> searchQuestion(String keyword, int offset, int limit) throws IOException, SolrServerException {
		List<Question> list = new ArrayList<>();
		
		StringBuilder queryString = new StringBuilder();
		queryString.append(QUESTION_TITLE_FIELD).append(":").append(keyword).append(" or ")
				.append(QUESTION_CONTENT_FIELD).append(":").append(keyword);
		
		SolrQuery query = new SolrQuery(queryString.toString()).setStart(offset).setRows(limit)
				.setHighlight(true).setHighlightSimplePre(HL_PRE).setHighlightSimplePost(HL_PSOT);
		query.set("hl.fl", QUESTION_TITLE_FIELD + "," + QUESTION_CONTENT_FIELD);
		
		QueryResponse response = solrClient.query(query);
		
		for (Map.Entry<String, Map<String, List<String>>> entry : response.getHighlighting().entrySet()) {
			Question q = new Question();
			q.setId(Integer.parseInt(entry.getKey()));
			if (entry.getValue().containsKey(QUESTION_CONTENT_FIELD)) {
				List<String> contentList = entry.getValue().get(QUESTION_CONTENT_FIELD);
				if (contentList.size() > 0) {
					q.setContent(contentList.get(0));
				}
			}
			if (entry.getValue().containsKey(QUESTION_TITLE_FIELD)) {
				List<String> titleList = entry.getValue().get(QUESTION_TITLE_FIELD);
				if (titleList.size() > 0) {
					q.setTitle(titleList.get(0));
				}
			}
			list.add(q);
		}
		return list;
	}
	
	public boolean indexQuestion(int qid, String title, String content) throws Exception {
		SolrInputDocument doc = new SolrInputDocument();
		doc.setField("id", qid);
		doc.setField(QUESTION_TITLE_FIELD, title);
		doc.setField(QUESTION_CONTENT_FIELD, content);
		UpdateResponse response = solrClient.add(doc, 1000);
		return response != null && response.getStatus() == 0;
	}
}
