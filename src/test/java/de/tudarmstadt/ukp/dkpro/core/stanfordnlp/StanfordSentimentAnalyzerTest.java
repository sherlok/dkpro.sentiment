package de.tudarmstadt.ukp.dkpro.core.stanfordnlp;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;
import de.tudarmstadt.ukp.dkpro.core.sentiment.type.StanfordSentimentAnnotation;
import org.apache.uima.cas.CAS;
import org.apache.uima.fit.component.CasDumpWriter;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.factory.TypeSystemDescriptionFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.util.CasCreationUtils;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class StanfordSentimentAnalyzerTest {

	@Test
	public void testSentiment() throws Exception {
		CAS cas = CasCreationUtils.createCas(TypeSystemDescriptionFactory.createTypeSystemDescription(), null, null);
		cas.setDocumentLanguage("en");
		cas.setDocumentText("I feel very very bad.");
		Sentence s = new Sentence(cas.getJCas(), 0, cas.getDocumentText().length());
		s.addToIndexes();

		SimplePipeline.runPipeline(cas,
				AnalysisEngineFactory.createEngineDescription(StanfordSentimentAnalyzer.class),

				AnalysisEngineFactory.createEngineDescription(CasDumpWriter.class));

		StanfordSentimentAnnotation sentimentAnnotation = JCasUtil.select(cas.getJCas(),
				StanfordSentimentAnnotation.class).iterator().next();
		assertTrue(sentimentAnnotation.getNegative() > 0);
	}
}