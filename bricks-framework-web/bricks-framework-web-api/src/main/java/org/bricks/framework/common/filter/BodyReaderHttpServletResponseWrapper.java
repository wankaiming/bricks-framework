package org.bricks.framework.common.filter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BodyReaderHttpServletResponseWrapper extends HttpServletResponseWrapper {

	protected final Logger log = LoggerFactory.getLogger(getClass());

	private int statusCode;

	private ByteArrayOutputStream bytes = new ByteArrayOutputStream();

	private PrintWriter pwrite;

	public BodyReaderHttpServletResponseWrapper(HttpServletResponse response) {
		super(response);
	}

	@Override
	public ServletOutputStream getOutputStream() throws IOException {
		return new MyServletOutputStream(bytes);
	}

	@Override
	public PrintWriter getWriter() throws IOException {
		try {
			pwrite = new PrintWriter(new OutputStreamWriter(bytes, "utf-8"));
		} catch (UnsupportedEncodingException e) {
			log.error("getWriter 出现异常", e);
		}
		return pwrite;
	}

	public String getBodyString() {
		try {
			return new String(getBytes(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			log.error("getBodyString 出现异常", e);
		}
		return "";
	}

	public byte[] getBytes() {
		if (null != pwrite) {
			pwrite.close();
			return bytes.toByteArray();
		}

		if (null != bytes) {
			try {
				bytes.flush();
			} catch (IOException e) {
				log.error("getBytes 出现异常", e);
			}
		}
		return bytes.toByteArray();
	}

	class MyServletOutputStream extends ServletOutputStream {
		private ByteArrayOutputStream ostream;

		public MyServletOutputStream(ByteArrayOutputStream ostream) {
			this.ostream = ostream;
		}

		@Override
		public void write(int b) throws IOException {
			ostream.write(b);
		}

		@Override
		public boolean isReady() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void setWriteListener(WriteListener arg0) {
			// TODO Auto-generated method stub
			
		}

	}

	@Override
	public void sendError(int sc, String msg) throws IOException {
		((HttpServletResponse) getResponse()).sendError(sc, msg);
		this.statusCode = sc;
	}

	@Override
	public void sendError(int sc) throws IOException {
		((HttpServletResponse) getResponse()).sendError(sc);
		this.statusCode = sc;
	}

	public void setStatus(int sc) {
		super.setStatus(sc);
		this.statusCode = sc;
	}

	public int getStatus() {
		return statusCode;
	}

}