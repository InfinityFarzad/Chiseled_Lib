chiseled lib is a library i made for Architektum Workshop to help with mod development and also to prevent us from repeatedly recoding the same thing for each project.

currently adds features such as:
  -screenshake
  -EnchantmentUtil
  -RenderUtil
  -ItemHighlightComponent

to import ChiseledLib, you have to add this line in your ``build.gradle``

```Groovy
  repositories {
	  maven {
		  url = "https://api.modrinth.com/maven"
	  }
  }

  dependencies {
	  modImplementation "maven.modrinth:chiseled-lib:${project.chiseled_lib_version}"
  }

```

and also add the chiseled_lib_version to your `gradle.properties` :

```Groovy
  chiseled_lib_version=1.0.0

```
you can find the latest version of the mod on our [Modrinth Page](https://modrinth.com/mod/chiseled-lib)

Links :

[Discord](https://discord.gg/pXVmqvHmRm)

[Juko_lul](https://bsky.app/profile/jukolul.bsky.social)

[InfinityFarzad](bsky.app/profile/infinityfarzad.bsky.social)

[Mongocat](https://www.youtube.com/@mongocat_wishs_merry_christmas)
