#!/usr/bin/env perl

use strict;
use warnings;

use File::Path;
use File::Copy;
use Archive::Zip qw( :ERROR_CODES :CONSTANTS );
use Cwd;


sub do_system {
    my $command = $_[0];
    ((system($command) >> 8) == 0) or die $!;
}

my $usage = "    perl make_distro.pl jsmoothcmd_path [version]\n";
my $jsmooth_path = $ARGV[0];
my $version = $ARGV[1];
my $output_dir = "out";
my $prefix = "SniffysFlipTrip";
my $jar_dir = "../out/artifacts/SniffysFlipTrip_jar";
my $jar_name = "SniffysFlipTrip.jar";
my $jar_path = "$jar_dir/$jar_name";
my $exe_name = "SniffysFlipTrip.exe";
my $readme_path = "readme.txt";
my $run_script_path = "run.sh";


# clean up old files
sub clean {
    if (-d $output_dir) {
        print("cleaning \"$output_dir\"\n");
        rmtree($output_dir) &&\
        mkdir($output_dir) or die $!;
    } else {
        print "making \"$output_dir\"\n" ;
        mkdir($output_dir) or die $!;
    }
}

sub make_common {
    my $subdir = $_[0];
    print($subdir."\n");
    print("Making $subdir.zip\n");
    my $path = "$output_dir/$subdir";
    mkdir($path) &&\
    copy($readme_path, $path) or die $!;
}

sub make_linux {
    my $version_string = $_[1];
    my $subdir = ($prefix)."_".($_[0]).($version_string);
    make_common($subdir);
    copy("$run_script_path", "$output_dir/$subdir");
    copy("$jar_path", "$output_dir/$subdir") or die $!;
    zip("$output_dir/$subdir");
}

sub make_windows {
    my $version_string = $_[0];
    my $subdir = "$prefix\_windows$version_string";
    
    make_common($subdir);
    my $cwd = getcwd();
    # jsmooth is a windows program so we cant pass it a cygwin path
    if (`uname` =~ m/cygwin/i) {
        $cwd = `cygpath -m $cwd`;
        chomp $cwd;
    }
    my $project_file = "$cwd/sft.jsmooth"; #this path must be absolute or jsmooth will crash
    do_system("$jsmooth_path $project_file");
    move("$exe_name", "$output_dir/$subdir") or die $!;
    zip("$output_dir/$subdir");
}

sub zip {
    my $dir = $_[0];

    my $zip = Archive::Zip->new();
    $zip->addTree("$dir", "SniffysFlipTrip/");
    ($zip->writeToFileNamed("$dir.zip") == AZ_OK) or die $!;
    rmtree($dir) or die $!;

}

sub main {

    if (!defined($jsmooth_path)) {
        print("Missing argument: jsmoothcmd path: the absolute path to the jsmoothcmd.exe executable.\n");
        print($usage);
        exit(1);
    }
    if (! -f $jar_path) {
        print("jar not found at $jar_dir, see github wiki for instructions\n");
        exit(1);
    }
    my $version_string;
    if (!defined($version)) {
        $version_string = "";
    } else {
        $version_string = "_v$version"
    }

    clean();
    make_linux("linux_mac", $version_string);
    make_windows($version_string);
    print("Success\n");
}

main();