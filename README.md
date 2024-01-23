
<div style="text-align:center; padding: 20px 20px 0;">
<img src=".github/assets/logo.png"  alt=""/>
<p><b>Lightweight plugin for enabling packet encryption on offline servers</b></p>
</div>

---

![license](https://img.shields.io/github/license/pesekjak/crypt4offline?style=for-the-badge&color=657185)
![release](https://img.shields.io/github/v/release/pesekjak/crypt4offline?style=for-the-badge&color=edb228)
![versions](https://img.shields.io/badge/supports-1.7%20—%201.20.4-8A2BE2?style=for-the-badge&color=0f9418)

**Crypt4Offline** is a lightweight plugin for Velocity proxy servers that enables packet encryption for offline servers.
All versions ranging from 1.7 to 1.20.4 are supported and the plugin is made future-proof to work on future versions as well.

Moreover, the plugin introduces a `cracked` property to every game profile. Its value is either `true` or `false` indicating whether the player
has been successfully authenticated on Mojang servers. This makes it possible for developers to differentiate between players having the original
copy of the game and cracked accounts on servers operating with online mode turned off.