package eu.transkribus.core.io;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

import eu.transkribus.core.model.beans.TrpPage;

public class ExportFilePatternUtils {

	public static final String FILENAME_PATTERN = "${filename}";
	public static final String DOCID_PATTERN = "${docId}";
	public static final String PAGEID_PATTERN = "${pageId}";
	public static final String PAGENR_PATTERN = "${pageNr}";	
	public static final String FILEKEY_PATTERN = "${filekey}";
	
	public static final String PAGENR_FILENAME_PATTERN = PAGENR_PATTERN+"_"+FILENAME_PATTERN;
	public static final String STANDARDIZED_PATTERN = DOCID_PATTERN+"_"+PAGENR_PATTERN+"_"+PAGEID_PATTERN;
	
	public static final String[] ALL_PATTERNS = new String[] {
			DOCID_PATTERN, FILENAME_PATTERN, PAGEID_PATTERN, PAGENR_PATTERN, FILEKEY_PATTERN
	};
			
	public static String buildBaseFileName(String fileNamePattern, TrpPage p) {
		if(StringUtils.isEmpty(fileNamePattern) || fileNamePattern.equals(FILENAME_PATTERN)) {
			return FilenameUtils.getBaseName(p.getImgFileName());
		} else {
			String fileName = buildBaseFileName(fileNamePattern, p.getImgFileName(), p.getPageId(), 
					p.getDocId(), p.getKey(), p.getPageNr());
			return fileName;
		}
	}

	public static String buildBaseFileName(String fileNamePattern, String imgFileName, int pageId, int docId,
			String key, int pageNr) {
		
		if(!isFileNamePatternValid(fileNamePattern)){
			throw new IllegalArgumentException("Filename pattern is invalid: " + fileNamePattern);
		}

		final String pageNrStr = StringUtils.leftPad(""+pageNr, 4, '0');
		final String docIdStr = StringUtils.leftPad(""+docId, 6, '0');
		
		String fileName = replacePatterns(fileNamePattern, FilenameUtils.getBaseName(imgFileName), ""+pageId, ""+docIdStr, key, pageNrStr);
		
		return fileName;
	}

	public static boolean isFileNamePatternValid(final String fnp) {
		//filename must have a unique component with respect to document
		boolean isValid = fnp.contains(FILENAME_PATTERN) || fnp.contains(FILEKEY_PATTERN) 
				|| fnp.contains(PAGEID_PATTERN) || fnp.contains(PAGENR_PATTERN);
		if(!isValid){
			return false;
		}
		//remove all valid placeholders	
		String fnpRemainder = replacePatterns(fnp, "", "", "", "", "");
		
		//check for occurence of illegal chars
		final String[] illegalChars = {"\\", "/", ":", "*", "?", "\"", "<", ">", "|", "~", "{", "}"};
		for(String s : illegalChars){
			if(fnpRemainder.contains(s)){
				return false;
			}
		}
		return true;
	}
	
	public static String replacePatterns(String stringWithPatterns, String filename, String pageId, String docId, String filekey, String pageNr) {
		String fileName = stringWithPatterns
				.replace(FILENAME_PATTERN, filename==null ? "" : filename)
				.replace(PAGEID_PATTERN, pageId==null ? "" : pageId)
				.replace(DOCID_PATTERN, docId==null ? "" : docId)
				.replace(FILEKEY_PATTERN, filekey==null ? "" : filekey)
				.replace(PAGENR_PATTERN, pageNr==null ? "" : pageNr);
		
		return fileName;
	}

}
