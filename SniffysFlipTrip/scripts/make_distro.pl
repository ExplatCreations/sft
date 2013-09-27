#!/usr/bin/env perl

use strict;
use warnings;

use File::Path;
use File::Copy;
use Cwd;


sub do_system {
    my $command = $_[0];
    ((system($command) >> 8) == 0) or die $!;
}


my $output_dir = $ARGV[0];
my $jar_location = "../out/artifacts/SniffysFlipTrip_jar";
my $jar_name = "SniffysFlipTrip.jar";
my $exe_name = "SniffysFlipTrip.exe";
my $readme_path = "readme.txt";
my $run_script_path = "run.sh";

# clean up old files
sub clean {
    if (-d $output_dir) {
        print("directory \"$output_dir\" exists, delete? [y/n].\n");
        my $ans = readline(*STDIN);
        if ($ans =~ m/y/i) {
            rmtree($output_dir) &&\
            mkdir($output_dir) or die $!;
        } else {
            print("aborting\n");
            exit(0);
        }
    } else {
        print "making $output_dir\n" ;
        mkdir($output_dir) or die $!;
    }
}

sub make_common {
    my $subdir = $_[0];
    print("Making $subdir build.\n");
    my $path = "$output_dir/$subdir";
    mkdir($path) &&\
    copy($readme_path, $path) or die $!;
}

sub make_linux {
    my $subdir = $_[0];
    make_common($subdir);
    copy("$run_script_path", "$output_dir/$subdir");
    copy("$jar_location/$jar_name", "$output_dir/$subdir") or die $!;
}

sub make_windows {
    my $subdir = "windows";
    make_common($subdir);
    my $jsmooth_path = "C:/Users/M/Downloads/JSmooth/jsmoothcmd.exe";
    my $cwd = getcwd();
    # jsmooth is a windows program so we cant pass it a cygwin path
    if (`uname` =~ m/cygwin/i) {
        $cwd = `cygpath -m $cwd`;
        chomp $cwd;
    }
    my $project_file = "$cwd/sft.jsmooth"; #this path must be absolute or jsmooth will crash
    do_system("$jsmooth_path $project_file");
    move("$exe_name", "$output_dir/$subdir") or die $!; #fixme -- relative path
}

sub main {
    if (!defined($output_dir)) {
        print("Missing argument: output directory.\n");
        exit(1);
    }

    clean();
    make_linux("linux");
    make_linux("mac");
    make_windows();
    print("Success");
}

main();