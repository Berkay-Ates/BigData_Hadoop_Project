package com.mapreduce.jobs.dailyReportCount;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;

public class DailyReportCountMapper extends MapReduceBase implements Mapper<LongWritable, Text, Text, IntWritable>{
    private final static IntWritable one = new IntWritable(1);

    public void map(LongWritable key, Text value, OutputCollector<Text, IntWritable> output, Reporter reporter) throws IOException {
        String valueString = value.toString();
        String[] splitValues = valueString.split(",");
        
        try {

                long unixTimestamp = Long.parseLong(splitValues[12]);
                Date date = new Date(unixTimestamp * 1000L); // Unix zaman damgası saniye cinsinden olduğu için 1000 ile çarpmamız gerekiyor
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String[] formattedDateTime = sdf.format(date).split(" ");

                output.collect(new Text(formattedDateTime[0]), one);

        } catch (Exception e) {
           System.err.println(e.toString());
        }

    }

}
