package org.apache.maven.dotnet;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.maven.dotnet.commons.project.VisualStudioProject;
import org.apache.maven.dotnet.commons.project.VisualStudioSolution;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.logging.Log;

/**
 * Generates a quality report for a .Net project or solution using mono gendarme
 * @goal gendarme
 * @phase site
 * @description generates a mono-gendarme report on a .Net project or solution
 * @author Alexandre Victoor
 *
 */
public class GendarmeMojo extends AbstractDotNetMojo {

	public final static String  DEFAULT_GENDARME_REPORT_NAME = "gendarme-report.xml";
	
	/**
   * Location of the mono gendarme installation
   * 
   * @parameter expression="${gendarme.directory}"
   */
  private File                gendarmeDirectory;
  
  /**
   * Name of the mono gendarme command line executable.
   * 
   * @parameter expression="${gendarme.executable}" default-value="gendarme.exe"
   */
  private String              gendarmeExecutable            = "gendarme.exe";
  
  /**
   * Name of the gendarme report file
   * 
   * @parameter alias="${fxCopReportName}" default-value="gendarme-report.xml"
   */
  private String              gendarmeReportName            = DEFAULT_GENDARME_REPORT_NAME;
  
  
  /**
   * Enable/disable the verbose mode for gendarme
   * 
   * @parameter expression="${verbose}"
   */
  private boolean             verbose;
  
  
  private File                executableFile;
	
  /**
   * Launches the report for a solution
   * 
   * @param solution the solution to check
   * @throws MojoFailureException
   * @throws MojoExecutionException
   */
  @Override
  protected void executeSolution(VisualStudioSolution solution) throws MojoFailureException, MojoExecutionException
  {
    List<VisualStudioProject> projects = solution.getProjects();
    List<File> checkedAssemblies = new ArrayList<File>();
    // We skip all the test assemblies
    for (VisualStudioProject visualStudioProject : projects)
    {
      if (!visualStudioProject.isTest())
      {
        File assembly = getGeneratedAssembly(visualStudioProject);
        if (assembly.exists())
        {
          checkedAssemblies.add(assembly);
        }
        else
        {
          getLog().info("Skipping the non generated assembly: " + assembly);
        }
      }
      else
      {
        getLog().info("Skipping the test project " + visualStudioProject.getName());
      }
    }
    launchReport(checkedAssemblies);
  }
  
  /**
   * Launches the report for a project.
   * 
   * @param visualProject the project to execute
   * @throws MojoFailureException
   * @throws MojoExecutionException
   */
  @Override
  protected void executeProject(VisualStudioProject visualProject) throws MojoFailureException, MojoExecutionException
  {
    if (visualProject.isTest())
    {
      getLog().info("No gendarme report generated for the test project " + visualProject.getName());
      return;
    }
    File assembly = getGeneratedAssembly(visualProject);
    if (!assembly.exists())
    {
      // No assembly found
      throw new MojoFailureException("Cannot find the generated assembly to launch gendarme " + assembly);
    }
    launchReport(Collections.singletonList(assembly));
  }

  
  /**
   * Launches the reporting for a list of assemblies
   * 
   * @param assemblies the assemblies to check
   * @throws MojoExecutionException if an execution problem occurred
   * @throws MojoFailureException in case of execution failure
   */
  protected void launchReport(List<File> assemblies) throws MojoExecutionException, MojoFailureException
  {
  	Log log = getLog();
    if (assemblies.isEmpty())
    {
      log.info("No assembly to check with FxCop");
      return;
    }
    
    // We retrieve the required files
    executableFile = new File(gendarmeDirectory, gendarmeExecutable);
    
    File reportFile = getReportFile(gendarmeReportName, DEFAULT_GENDARME_REPORT_NAME);

    // We build the command arguments
    List<String> commandArguments = new ArrayList<String>();

   
    // Defines the report file
    log.debug("- Report file  : " + reportFile);
    commandArguments.add("--xml");
    commandArguments.add(toCommandPath(reportFile));
    
    // Put in verbose mode if required
    if (verbose)
    {
      commandArguments.add("/v");
    }
    // Add the assemblies to check
    log.debug("- Scanned assemblies :");
    for (File checkedAssembly : assemblies)
    {
      log.debug("   o " + checkedAssembly);
      commandArguments.add(toCommandPath(checkedAssembly));
    }
    
    // We launch the command (and we accept the reference problems)
    launchCommand(executableFile, commandArguments, "gendarme", 0, true);
    log.info("gendarme report generated");
  }

}
