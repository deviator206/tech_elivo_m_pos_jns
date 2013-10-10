/*
 * Copyright (C) 2013 USAP Pvt. Ltd. All Rights Reserved.
 * USAP PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mpos.framework.common;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.app.Application;
import android.os.Environment;
import android.util.Log;

/**
 * Class is used to send log messages to logcat and simultaneously write to log
 * file on sdcard.
 * <p>
 * The order in terms of verbosity, from least to most is ERROR, WARN, INFO,
 * DEBUG, VERBOSE. Verbose should never be compiled into an application except
 * during development. Debug logs are compiled in but stripped at runtime.
 * Error, warning and info logs are always kept.
 * <p>
 * <b>Note:</b>
 * <li>Do not call {@link Logger#deleteOldFile()} method if old days log files
 * are require for debugging.
 * <li>
 * Always call {@link Logger#deleteOldFile()} method after setting logger file
 * name prefix using {@link Logger#setLoggerFileName(String)} method.
 * <p>
 * <b>Tip:</b> A good convention is to declare a <code>TAG</code> constant in
 * your class: <br>
 * <p>
 * * <b>How to use:</b> Initialize Logger either in Launcher activity or
 * {@link Application} class by calling following code of snippet.
 * <p>
 * Logger.setLogging(BuildConfig.DEBUG);<br>
 * Logger.setLoggerFileName("Name of application");<br>
 * Logger.deleteOldFile();<br>
 * 
 */
public class Logger {

	private static String mName = "";

	private static String TAG = "Logger";
	private static SimpleDateFormat stSDFLoggerMessageTime = new SimpleDateFormat(
			"MM/dd/yyyy hh:mm:ss:SS aa", Locale.getDefault());
	private static SimpleDateFormat stSDFFileWithDateIdentifier = new SimpleDateFormat(
			"MM/dd/yyyy", Locale.getDefault());
	static File root;
	private static File stLoggerFile;
	static FileWriter stLoggerFileWriter;
	static BufferedWriter stBufferedWriter;
	private static int stCurrentDate = Integer
			.valueOf(stSDFFileWithDateIdentifier.format(new Date()).replaceAll(
					"/", ""));
	private static boolean LOGGING = true;
	private static boolean stIsFileInitialized = false;
	private static String stLogDirectory = "/Log_Report";

	static {
		// Create directory on SDCARD to save log files.
		File file = new File(Environment.getExternalStorageDirectory(),
				stLogDirectory);
		if (file.mkdir() == false) {
			if (LOGGING)
				Log.e(TAG, "Failed to create log directory");
		}

	}

	/**
	 * Check previous initialized date with current date if both date does not
	 * match each other, it deletes old log file(yesterday) and creates new file
	 * with today's date.
	 */
	private static void checkDate() {

		int i = Integer.valueOf(stSDFFileWithDateIdentifier.format(new Date())
				.replaceAll("/", ""));

		if (i != stCurrentDate) {
			stCurrentDate = Integer.valueOf(stSDFFileWithDateIdentifier.format(
					new Date()).replaceAll("/", ""));
			root = new File(Environment.getExternalStorageDirectory(),
					stLogDirectory);

			stLoggerFile = new File(root, getFileName());
			// deleteOldFile();
			try {
				stLoggerFileWriter = new FileWriter(stLoggerFile, true);
				stBufferedWriter = new BufferedWriter(stLoggerFileWriter);

			} catch (IOException e) {

			}
		} else {
			try {
				stLoggerFileWriter = new FileWriter(stLoggerFile, true);
				stBufferedWriter = new BufferedWriter(stLoggerFileWriter);
			} catch (IOException e) {

			}
		}
	}

	/**
	 * Send message to logcat and sdcard.
	 * 
	 * @param tag
	 *            To identify the message.
	 * @param message
	 *            The message to be logged.
	 */
	public static void d(String tag, String message) {

		if (LOGGING) {
			android.util.Log.d(tag, message);
			writeToLogcatAndSDCARD(tag, message);
		}
	}

	/**
	 * Delete all files of the application except today's log file.
	 */
	public static void deleteOldFile() {
		// delete the old file
		root = new File(Environment.getExternalStorageDirectory(),
				stLogDirectory);
		if (root != null) {
			if (root.listFiles() != null) {
				for (File file : root.listFiles()) {
					if (file != null) {
						if (file.getName().startsWith(mName + "_Logreport")) {
							if (!file.getName().equalsIgnoreCase(getFileName())) {
								file.delete();
							}
						}
					}
				}
			}
		}
	}

	/**
	 * Send message to logcat and sdcard.
	 * 
	 * @param tag
	 *            To identify the message.
	 * @param message
	 *            The message to be logged.
	 */
	public static void e(String tag, String message) {

		if (LOGGING) {
			android.util.Log.e(tag, message);
			writeToLogcatAndSDCARD(tag, message);
		}
	}

	private static String getCurrentDateTime() {
		return stSDFLoggerMessageTime.format(new Date());
	}

	private static String getFileName() {
		String szCurrentDate = getFormatedDateString().replaceAll("/", "");
		String szFileName = mName + "_Logreport" + szCurrentDate + ".txt";

		return szFileName;
	}

	private static String getFormatedDateString() {
		return stSDFFileWithDateIdentifier.format(new Date());
	}

	/**
	 * Send message to logcat and sdcard.
	 * 
	 * @param tag
	 *            To identify the message.
	 * @param message
	 *            The message to be logged.
	 */
	public static void i(String tag, String message) {

		if (LOGGING) {
			android.util.Log.i(tag, message);
			writeToLogcatAndSDCARD(tag, message);
		}
	}

	private static void initializeFile() {

		root = new File(Environment.getExternalStorageDirectory(),
				stLogDirectory);

		// create file.
		stLoggerFile = new File(root, getFileName());

		try {
			stLoggerFileWriter = new FileWriter(stLoggerFile, true);
			stBufferedWriter = new BufferedWriter(stLoggerFileWriter);
			stIsFileInitialized = true;
		} catch (IOException e) {
			if (LOGGING)
				Log.e(TAG,
						"Failed to create file on the sdcard. PLease check whether SDcard is mounted. Message:"
								+ e.getMessage());
		}

	}

	/**
	 * To identify log report file on the sdcard.
	 * 
	 * @param name
	 *            : Name to be prefix with complete file name.
	 */
	public static void setLoggerFileName(String name) {
		mName = name;
		initializeFile();
	}

	/**
	 * Turn on/off logging to logcat and sdcard
	 * 
	 * @param logging
	 *            true: turn ON false : turn OFF
	 */
	public static void setLogging(boolean logging) {
		LOGGING = logging;
	}

	/**
	 * Send message to logcat and sdcard.
	 * 
	 * @param tag
	 *            To identify the message.
	 * @param message
	 *            The message to be logged.
	 */
	public static void v(String tag, String message) {

		if (LOGGING) {
			android.util.Log.v(tag, message);
			writeToLogcatAndSDCARD(tag, message);
		}
	}

	/**
	 * Send message to logcat and sdcard.
	 * 
	 * @param tag
	 *            To identify the message.
	 * @param message
	 *            The message to be logged.
	 */
	public static void w(String tag, String message) {

		if (LOGGING) {
			android.util.Log.w(tag, message);
			writeToLogcatAndSDCARD(tag, message);
		}
	}

	/**
	 * Write tag and message along with current time.
	 * 
	 * @param tag
	 *            To identify the message
	 * @param message
	 *            The message to be logged.
	 */
	private static void writeToLogcatAndSDCARD(String tag, String message) {

		if (stIsFileInitialized == false) {
			initializeFile();
		}

		try {

			if (root.canWrite()) {
				checkDate();
				String line_seperator = "\n";
				String text = getCurrentDateTime() + " " + tag + " " + message
						+ line_seperator;
				if (stBufferedWriter == null) {
					stLoggerFileWriter = new FileWriter(stLoggerFile, true);
					stBufferedWriter = new BufferedWriter(stLoggerFileWriter);
				}
				stBufferedWriter.write(text);
				stBufferedWriter.flush();

			} else {
				if (LOGGING)
					Log.e(TAG,
							"Application does not have permission to write log file");
			}

		} catch (IOException e) {
			if (LOGGING)
				Log.e(TAG, "Could not write file " + e.getMessage());
		} catch (Exception e) {

		}
	}
}