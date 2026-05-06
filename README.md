**ChiseledLib** is a library made for [Architektum Workshop](https://modrinth.com/organization/architektum-workshop) to help with mod development.

## Features
**Currently adds features such as**:
  - Screenshake
  - EnchantmentUtil & RenderUtil
  - Item Highlighting
  - Custom Item effects and Sweeping item to make your life easier when making weapons

**W.I.P/Planned** :
- Easing and interpolation utility
- RegistryUtil **(DO NOT USE THE CURRENT VERSION)**
  
### How to use

while ChiseledLib is meant to be primarily used by **Architektum workshop**, you can still import it for your own projects if you want.
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

and also add `chiseled_lib_version` to your `gradle.properties` containing the version id of your desired version of ChiseledLib, lets say i want to import version 2.0.1, in which case the version id would be 2.0.1

```Groovy
  chiseled_lib_version=2.0.1
```
you can find the latest version of the mod on the [ChiseledLib Modrinth Page](https://modrinth.com/mod/chiseled-lib)

### Extra Info 

Links :

[Discord](https://discord.gg/pXVmqvHmRm)

[Architektum Workshop](https://modrinth.com/organization/architektum-workshop)

[ChiseledLib](https://modrinth.com/mod/chiseled-lib)


Credits :

[Powercyphe](https://github.com/Powercyphe) -> Cleaning up a lot of the code for version 2.0.0 and handling the 1.21.11 port

[Mongocat](https://www.youtube.com/@mongocat_wishs_merry_christmas) -> The Artist of the project branding (banner, icon, plushes)

[Juko_lul](https://bsky.app/profile/jukolul.bsky.social)

[InfinityFarzad](https://bsky.app/profile/infinityfarzad.bsky.social) -> The Original creator of the library
