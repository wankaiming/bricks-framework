package org.bricks.framework.common.validator.impl;

import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.bricks.framework.common.validator.SafeTextOrHtml;

/**
 * xss 检查注解
 * 
 * @author wankaiming
 * @time 2018年12月28日 下午6:01:09
 */
public class SafeTextOrHtmlValidator implements ConstraintValidator<SafeTextOrHtml, String> {

	private static final String patternEvent = "([\\s\\S]*)(onafterprint|onbeforeprint|onbeforeonload|onblur|onerror|onfocus|onhaschange|onload|onmessage|onoffline|ononline|onpagehide|onpageshow|onpopstate|onredo|onresize|onstorage|onundo|onunload|onblur|onchange|oncontextmenu|onfocus|onformchange|onforminput|oninput|oninvalid|onreset|onselect|onsubmit|onkeydown|onkeypress|onkeyup|onclick|ondblclick|ondrag|ondragend|ondragenter|ondragleave|ondragover|ondragstart|ondrop|onmousedown|onmousemove|onmouseout|onmouseover|onmouseup|onmousewheel|onscroll|onabort|oncanplay|oncanplaythrough|ondurationchange|onemptied|onended|onerror|onloadeddata|onloadedmetadata|onloadstart|onpause|onplay|onplaying|onprogress|onratechange|onreadystatechange|onseeked|onseeking|onstalled|onsuspend|ontimeupdate|onvolumechange|onwaiting|onshow|ontoggle)([\\s]*)\\=([\\s\\S]*)";

	private static final String patternScrpit = "([\\s\\S]*)\\<([\\s]*)script([\\s]*)\\>([\\s\\S]*)\\<([\\s]*)\\/([\\s]*)script([\\s]*)\\>([\\s\\S]*)";

	private static final String patternJavaScrpit = "([\\s\\S]*)javascript([\\s]*)\\:([\\s\\S]*)";

	private static final Pattern patternEventObj = Pattern.compile(patternEvent, Pattern.CASE_INSENSITIVE);

	private static final Pattern patternScrpitObj = Pattern.compile(patternScrpit, Pattern.CASE_INSENSITIVE);

	private static final Pattern patternJavaScrpitObj = Pattern.compile(patternJavaScrpit, Pattern.CASE_INSENSITIVE);

	@Override
	public void initialize(SafeTextOrHtml constraintAnnotation) {

	}

	/**
	 * @param value
	 * @param context
	 * @return
	 * 
	 * @author wankaiming
	 * @time 2018年12月28日 下午6:01:09
	 */
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (null != value && !"".equals(value)) {
			String tem = value.toLowerCase();
			if (patternEventObj.matcher(tem).matches() 
					|| patternScrpitObj.matcher(tem).matches()
					|| patternJavaScrpitObj.matcher(tem).matches()) {
				return false;
			}
		}
		return true;
	}

}
