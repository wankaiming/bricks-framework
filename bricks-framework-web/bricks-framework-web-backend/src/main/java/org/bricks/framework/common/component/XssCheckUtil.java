package org.bricks.framework.common.component;

import java.util.regex.Pattern;

public class XssCheckUtil {

	private static final String patternEvent = "([\\s\\S]*)(onafterprint|onbeforeprint|onbeforeonload|onblur|onerror|onfocus|onhaschange|onload|onmessage|onoffline|ononline|onpagehide|onpageshow|onpopstate|onredo|onresize|onstorage|onundo|onunload|onblur|onchange|oncontextmenu|onfocus|onformchange|onforminput|oninput|oninvalid|onreset|onselect|onsubmit|onkeydown|onkeypress|onkeyup|onclick|ondblclick|ondrag|ondragend|ondragenter|ondragleave|ondragover|ondragstart|ondrop|onmousedown|onmousemove|onmouseout|onmouseover|onmouseup|onmousewheel|onscroll|onabort|oncanplay|oncanplaythrough|ondurationchange|onemptied|onended|onerror|onloadeddata|onloadedmetadata|onloadstart|onpause|onplay|onplaying|onprogress|onratechange|onreadystatechange|onseeked|onseeking|onstalled|onsuspend|ontimeupdate|onvolumechange|onwaiting|onshow|ontoggle)([\\s]*)\\=([\\s\\S]*)";

	private static final String patternScrpit = "([\\s\\S]*)\\<([\\s]*)script([\\s\\S]*)\\>([\\s\\S]*)\\<([\\s]*)\\/([\\s]*)script([\\s]*)\\>([\\s\\S]*)";

	private static final String patternJavaScrpit = "([\\s\\S]*)javascript([\\s]*)\\:([\\s\\S]*)";
	
	private static final Pattern patternEventObj = Pattern.compile(patternEvent, Pattern.CASE_INSENSITIVE);

	private static final Pattern patternScrpitObj = Pattern.compile(patternScrpit, Pattern.CASE_INSENSITIVE);

	private static final Pattern patternJavaScrpitObj = Pattern.compile(patternJavaScrpit, Pattern.CASE_INSENSITIVE);

	/**
	 * 判断是否存在xss脚本
	 * 
	 * @param value
	 * @return
	 * @author wankaiming
	 * @time 2019年5月28日 下午6:40:32
	 */
	public static boolean checkIsExistXss(String value) {
		if (null != value && !"".equals(value)) {
			String tem = value.toLowerCase();
			if (patternEventObj.matcher(tem).matches() || patternScrpitObj.matcher(tem).matches()
					|| patternJavaScrpitObj.matcher(tem).matches()) {
				return true;
			}
		}
		return false;
	}

}
