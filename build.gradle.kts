import net.minecraftforge.gradle.user.UserBaseExtension

// TODO: rewrite gradle to use fg5

buildscript {
    repositories {
        mavenCentral()
        maven {
            name = "forge"
            setUrl("https://maven.minecraftforge.net/")
        }
    }
    dependencies {
        classpath("net.minecraftforge.gradle:ForgeGradle:2.3-SNAPSHOT")
        classpath("org.eclipse.jgit:org.eclipse.jgit:5.8.0.202006091008-r")
        classpath("org.apache.commons:commons-lang3:3.12.0")
    }
}

plugins {
    id("com.matthewprenger.cursegradle") version "1.1.0"
    id("maven-publish")
}

apply {
    plugin("net.minecraftforge.gradle.forge")
}

val mcVersion = "1.12.2"
val forgeVersion = "14.23.5.2847"
val mcFullVersion = "$mcVersion-$forgeVersion"
val modVersion = "0.2.3-beta"
version = "$mcVersion-$modVersion"
group = "net.htmlcsjs.htmlTech"

configure<BasePluginConvention> {
    archivesBaseName = "htmlTech"
}

fun minecraft(configure: UserBaseExtension.() -> Unit) = project.configure(configure)

minecraft {
    version = mcFullVersion
    mappings = "stable_39"
    runDir = "run"
    replace("@VERSION@", modVersion)
    replaceIn("HTValues.java")
}
repositories {
    maven {
        setUrl("https://minecraft.curseforge.com/api/maven")
    }
    maven {
        setUrl("http://chickenbones.net/maven/")
    }
    maven {
        setUrl("http://dvs1.progwml6.com/files/maven/")
    }
    maven { // TOP
        name = "tterrag maven"
        setUrl("https://maven.tterrag.com/")
    }
    maven {
        setUrl("https://cursemaven.com")
    }
    maven {
        name = "CraftTweaker Maven"
        setUrl("https://maven.blamejared.com/")
    }
    maven {
        setUrl("https://maven.cleanroommc.com")
    }
    maven {
        name = "maverinth"
        setUrl("https://api.modrinth.com/maven")
    }
}

dependencies {
    "deobfCompile"("mezz.jei:jei_1.12.2:+")
    "deobfCompile"("maven.modrinth:gregtech-ce-unofficial:2.6.1") // 2.6.1
    "deobfCompile"("codechicken-lib-1-8:CodeChickenLib-1.12.2:3.2.3.358:universal")
    "deobfCompile"("codechicken:ChickenASM:1.12-1.0.2.9")
    "deobfCompile"("mcjty.theoneprobe:TheOneProbe-1.12:1.12-1.4.23-16")
    "deobfCompile"("team.chisel.ctm:CTM:MC1.12.2-1.0.2.31")
    "deobfCompile"("CraftTweaker2:CraftTweaker2-MC1120-Main:1.12-4.1.20.655")
//    "deobfCompile"("curse.maven:groovyscript-687577:4399621")
    "provided"(files("libs/groovyscript-0.4.0.jar"))
    "deobfCompile"("zone.rong:mixinbooter:4.2") // fuck rong
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

val processResources: ProcessResources by tasks
val sourceSets: SourceSetContainer = the<JavaPluginConvention>().sourceSets

processResources.apply {
    inputs.property("version", modVersion)
    inputs.property("mcversion", mcFullVersion)

    from(sourceSets["main"].resources.srcDirs) {
        include("mcmod.info")
        expand(mapOf("version" to modVersion,
            "mcversion" to mcFullVersion))
    }

    // copy everything else, thats not the mcmod.info
    from(sourceSets["main"].resources.srcDirs) {
        exclude("mcmod.info")
    }
}
