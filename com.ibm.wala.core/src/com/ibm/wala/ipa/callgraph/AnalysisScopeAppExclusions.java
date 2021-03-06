package com.ibm.wala.ipa.callgraph;

import java.util.Collection;
import java.util.Collections;

import com.ibm.wala.classLoader.IClass;
import com.ibm.wala.classLoader.Language;
import com.ibm.wala.types.ClassLoaderReference;
import com.ibm.wala.util.config.SetOfClasses;

/**
 * Class to specify an analysis scope where some classes loaded by the
 * application loader will not be considered "application" classes. This is
 * useful when analyzing programs where library code has been added into the jar
 * and thus would normally be treated as "application" code by WALA.
 */
public class AnalysisScopeAppExclusions extends AnalysisScope {

  private SetOfClasses applicationExclusions;

  public static AnalysisScopeAppExclusions createJavaAnalysisScope(SetOfClasses applicationExclusions) {
    AnalysisScopeAppExclusions scope = new AnalysisScopeAppExclusions(Collections.singleton(Language.JAVA), applicationExclusions);
    scope.initForJava();
    return scope;
  }

  protected AnalysisScopeAppExclusions(Collection<? extends Language> languages, SetOfClasses applicationExclusions) {
    super(languages);
    this.applicationExclusions = applicationExclusions;
  }

  @Override
  public boolean isApplicationClass(IClass klass) {
    return klass.getClassLoader().getReference().equals(ClassLoaderReference.Application)
        && !applicationExclusions.contains(klass.getName().toString().substring(1));
  }
}
