![banner](.github/assets/logo.png)

<p align="center"><b>PrimalLib</b> is a library designed
specifically for <i>Paper</i> (not Spigot) plugins,
offering a range of additional features that are not included
in the standard API, developed with flexibility and
efficiency in mind.</p>

<p align="center">
    <img src="https://img.shields.io/github/license/pesekjak/primallib?style=for-the-badge&color=107185" alt="LICENSE">
    <img src="https://img.shields.io/github/v/release/pesekjak/primallib?style=for-the-badge&color=edb228" alt="RELEASE">
    <img src="https://img.shields.io/badge/supports-%201.20.4-8A2BE2?style=for-the-badge&color=0f9418" alt="SUPPORTS">
</p>

---

# Table of contents
* [Features](#features)
* [Importing](#importing)
  * [Using the API](#using-the-api)
* [License](#license)

---

## Features

* Advancements API
  * PrimalLib allows developers to fully manage advancements visible
    to each player, including modification of currently existing
    advancements.


* Auth API
  * PrimalLib contains multiple classes for easier interactions with
    Mojang services for getting player skins and UUIDs.


* Client Configuration
  * PrimalLib allows client-side modification of server data-driven
    registries and tags sent during the configuration phase.


* NBT Integration
  * PrimalLib integrates Adventure's NBT library for ItemStacks and
    PDC holders.
  * Adventure NBT is utilized at other places as well such as
    data-driven registries.


* And More
  * These are only some of the extra features PrimalLib offers.
    Developers are encouraged to explore the source code and
    discover additional APIs! :)

---

## Importing

### Using the API

PrimalLib utilizes the newly introduced bootstrapper API for Paper plugins.
To use it you need to replace the plugin's bootstrapper with one provided by
PrimalLib that will delegate all methods calls to the original one (so no functionality
is lost). This allows PrimalLib to register its own listeners and services without
the requirement of extra plugin dependency or other special initialization.

Here's an example of `paper-plugin.yml` for a plugin using PrimalLib.

```yaml
# PrimalLib's bootstrapper
bootstrapper: org.machinemc.primallib.internal.PrimalLibBootstrap

# Plugin's own bootstrapper
delegate-bootstrapper: foo.bar.Bootstrap
```

Working example can be seen in the `test-plugin` module in this project.

> [!NOTE]
> If you relocate the PrimalLib package, do not forget to change the path for the bootstrapper.

---

## License

PrimalLib is free software licensed under the [MIT license](LICENSE).