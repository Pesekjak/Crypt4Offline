![banner](.github/assets/logo.png)

<p align="center"><b>Lightweight plugin for enabling packet encryption on offline servers</b></p>

<p align="center">
    <img src="https://img.shields.io/github/license/pesekjak/crypt4offline?style=for-the-badge&color=107185" alt="LICENSE">
    <img src="https://img.shields.io/github/v/release/pesekjak/crypt4offline?style=for-the-badge&color=edb228" alt="RELEASE">
    <img src="https://img.shields.io/badge/supports-1.7%20—%201.20.4-8A2BE2?style=for-the-badge&color=0f9418" alt="SUPPORTS">
</p>

---

**Crypt4Offline** is a lightweight plugin for Velocity proxy servers that enables packet encryption for offline servers.
All versions ranging from 1.7 to 1.20.4 are supported and the plugin is made future-proof to work on future versions as well.

In addition to encryption, the plugin also enables the player heads visual for the server player list.

|                           |                                       |
|---------------------------|---------------------------------------|
| Server player list before | ![before](./.github/assets/empty.png) |
| Server player list after  | ![after](./.github/assets/heads.png)  |

Moreover, the plugin introduces a `cracked` property to every game profile. Its value is either `true` or `false` indicating whether the player
has been successfully authenticated on Mojang servers. This makes it possible for developers to differentiate between players having the original
copy of the game and cracked accounts on servers operating with online mode turned off.

---

### License
Crypt4Offline is free software licensed under the [MIT license](LICENSE).

Portions of this code were adapted from [PaperMC/Velocity](https://github.com/PaperMC/Velocity), licensed under the [GPL 3.0](VELOCITY_LICENSE).